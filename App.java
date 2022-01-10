import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class App {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        for (int i = 0; i < main.planszaGraczaLodkiarray.length; i++) {
          main.planszaGraczaLodkiarray[i] = new tile(false,false,false,"");
        }
        for (int i = 0; i < main.planszaGraczaStrzalyarray.length; i++) {
          main.planszaGraczaStrzalyarray[i] = new tile(false,false,false,"");
        }
        for (int i = 0; i < main.planszaBotaLodkiarray.length; i++) {
          main.planszaBotaLodkiarray[i] = new tile(false,false,false,"");
        }
        for (int i = 0; i < main.planszaBotaStrzalyarray.length; i++) {
          main.planszaBotaStrzalyarray[i] = new tile(false,false,false,"");
        }
        for (int i = 0; i < main.ghostPlanszaBotaLodkiarray.length; i++) {
          main.ghostPlanszaBotaLodkiarray[i] = new tile(false,false,false,"");
        }
        for(int i = 0;i<5;i++){
            main.botaLodkiNieZatopione.add(main.ships[i].shipName);
            main.graczaLodkiNieZatopione.add(main.ships[i].shipName);
        }
        int poczatekLinji = 0;
        for(int i = 0;i<3;i++){
            for(int c = 0;c < 3;c++){
              for(int b = 0;b < 3;b++){
                  main.botShootCheckerboard.add(poczatekLinji + (3 * b));
                  main.botShootCheckerboardBackup.add(poczatekLinji + (3 * b));
              }
              poczatekLinji += 10;
            }
            poczatekLinji += -3;
        }
        Random random = new Random();
        int randomCheckerboardShift = random.nextInt(3);
        if(randomCheckerboardShift != 0){
            for(int i = 0;i < main.botShootCheckerboard.size();i++){
                if(((main.botShootCheckerboard.get(i) + randomCheckerboardShift) % 9 == 0 || ((main.botShootCheckerboard.get(i) + randomCheckerboardShift) - 1) % 9 == 0 ) && (main.botShootCheckerboard.get(i) % 9 != 0)){
                    main.botShootCheckerboard.set(i, main.botShootCheckerboard.get(i) - 9);
                    main.botShootCheckerboardBackup.set(i, main.botShootCheckerboardBackup.get(i) - 9);
                }
                main.botShootCheckerboard.set(i, main.botShootCheckerboard.get(i) + randomCheckerboardShift);
                main.botShootCheckerboardBackup.set(i, main.botShootCheckerboardBackup.get(i) + randomCheckerboardShift);
            }
        }
        System.out.println("Welcome to Battleships");
        System.out.println("Press Enter To Start");
        System.in.read();
        System.out.println("Firstly, input the number of adjacent squares corresponding to the current ship size 1 at a time (no diagnals and only straight line placements)" + "\r\n");
        main.poczatkowelodzie();
        main.botKladzLodzie();
        while(main.gameOver == 0){
            main.graczStrzela();
            main.aktualizujPlansze();
            main.devAktualizujPlanszeBota();
            System.out.print(main.messageToWrite + "\r\n");
            boolean anyShips = false;
            for(int i = 0;i<81;i++){
                if(main.planszaBotaLodkiarray[i].containsShip == true){
                    anyShips = true;
                }
            }
            if(anyShips == false){
                main.gameOver = 1;
                break;
            }
            System.out.print("Bot move");
            Thread.sleep(1000);
            System.out.print(".");
            Thread.sleep(1000);
            System.out.print("\r\n");
            main.botStrzela();
            main.aktualizujPlansze();
            main.devAktualizujPlanszeBota();
            System.out.print(main.messageToWrite + "\r\n");
            anyShips = false;
            for(int i = 0;i<81;i++){
                if(main.planszaGraczaLodkiarray[i].containsShip == true){
                    anyShips = true;
                }
            }
            if(anyShips == false){
                main.gameOver = 2;
            }
        }
        System.out.println("Game Over\r\n");
        if(main.gameOver == 1){
            System.out.println("You win!\r\n");
            System.exit(0);
        }
        else{
            System.out.println("You loose :-(\r\n");
            System.exit(0);
        }
    }
}
class tile{
    boolean containsShip = false;

    boolean shot = false;
    boolean sunken = false;
    String shipName = "";

    public tile(boolean containsShip, boolean shot, boolean sunken, String shipName){
        this.containsShip = containsShip;

        this.shot = shot;
        this.sunken = sunken;
        this.shipName = shipName;
    }
}
class ship{
    String shipName = "";
    int length = 0;
    public ship(String shipName, int length){
        this.shipName = shipName;
        this.length = length;
    }
}
class Main{
    public String planszaCzysta = "  |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   ";
    
    public String planszaGraczaLodki = "  |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   ";
    public String planszaGraczaStrzaly = "  |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   ";

    public String planszaBotaLodki = "  |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   ";
    public String planszaBotaStrzaly = "  |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   ";

    public tile[] planszaGraczaLodkiarray = new tile[81];
    public tile[] planszaGraczaStrzalyarray = new tile[81];

    public tile[] planszaBotaLodkiarray = new tile[81];
    public tile[] planszaBotaStrzalyarray = new tile[81];

    public boolean GraczashipsCompleted = false;
    public int gameOver = 0;
    public ArrayList<Integer> graczaStrzaly = new ArrayList<>();
    public ArrayList<Integer> botaStrzaly = new ArrayList<>();
    public ArrayList<String> botaLodkiNieZatopione = new ArrayList<>();
    public ArrayList<String> graczaLodkiNieZatopione = new ArrayList<>();
    public String messageToWrite = "";
    public String wynikOstatniegoRuchuBota = "";

    public int lastBotMove;
    public boolean lastBotMoveWasHit;
    public int firstBotSuccessfullHit;
    public int botShootMode = 0;
    public int botBoatShootDirection;
    public ArrayList<Integer> botShootMode1list = new ArrayList<>();
    public ArrayList<Integer> botShootCheckerboard = new ArrayList<>();
    public ArrayList<Integer> botShootCheckerboardBackup = new ArrayList<>();

    public boolean beenAt0 = true;

    public int arrayPosition = -1;
    public boolean rotated = false;

    public boolean botShootMode0Finished = false;
    public boolean botShootMode1Finished = false;
    public boolean botShootMode2Finished = false;

    public ArrayList<Integer> probujemyDoOkola = new ArrayList<Integer>();
    
    public int botStrzelaCallMethodTotal = 0;

    public Scanner reader = new Scanner(System.in);

    public tile[] ghostPlanszaBotaLodkiarray = new tile[81];
    public String ghostPlanszaBotaLodki = "  |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   ";

    public ship[] ships = {new ship("Carrier", 5), new ship("Battleship", 4), new ship("Cruiser", 3), new ship("Submarine", 3), new ship("Destroyer", 2)};

    public void aktualizujPlansze(){
        StringBuilder planszaGraczaLodkistrbld = new StringBuilder(planszaGraczaLodki);
        for(int i = 0;i<81;i++){
            if(planszaGraczaLodkiarray[i].shot == true){
                planszaGraczaLodkistrbld.setCharAt( i * 4, '·');
            }
            if(planszaGraczaLodkiarray[i].containsShip == true){
                planszaGraczaLodkistrbld.setCharAt( i * 4, '■');
            }
            if(planszaGraczaLodkiarray[i].sunken == true){
                planszaGraczaLodkistrbld.setCharAt( i * 4, 'X');
            }
            if(planszaGraczaLodkiarray[i].containsShip == false && planszaGraczaLodkiarray[i].sunken == true){
                planszaGraczaLodkistrbld.setCharAt( i * 4, 'Ø');
            }
        }
        planszaGraczaLodki = planszaGraczaLodkistrbld.toString();

        StringBuilder planszaGraczaStrzalystrbld = new StringBuilder(planszaGraczaStrzaly);
        for(int i = 0;i<81;i++){
            if(planszaGraczaStrzalyarray[i].shot == true){
                planszaGraczaStrzalystrbld.setCharAt( i * 4, '·');
            }
            if(planszaGraczaStrzalyarray[i].sunken == true){
                planszaGraczaStrzalystrbld.setCharAt( i * 4, 'X');
            }
            if(planszaGraczaStrzalyarray[i].containsShip == false && planszaGraczaStrzalyarray[i].sunken == true){
                planszaGraczaStrzalystrbld.setCharAt( i * 4, 'Ø');
            }
        }
        planszaGraczaStrzaly = planszaGraczaStrzalystrbld.toString();

        System.out.println("\u001B[32m\r\n            Your Board");
        System.out.println(" A  B   C   D   E   F   G   H   I");
        System.out.println(planszaGraczaLodki.substring(0,33) + " 1" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaLodki.substring(36,69)+ " 2" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaLodki.substring(72,105)+ " 3" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaLodki.substring(108,141)+ " 4" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaLodki.substring(144,177)+ " 5" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaLodki.substring(180,213)+ " 6" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaLodki.substring(216,249)+ " 7" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaLodki.substring(252,285)+ " 8" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaLodki.substring(288,321)+ " 9" + "\r\n");
        System.out.println("\u001B[33m            Enemy Board");
        System.out.println(" A  B   C   D   E   F   G   H   I");
        System.out.println(planszaGraczaStrzaly.substring(0,33) + " 1" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaStrzaly.substring(36,69)+ " 2" +"\r\n" + "----------------------------------" + "\r\n" + planszaGraczaStrzaly.substring(72,105)+ " 3" +"\r\n" + "----------------------------------" + "\r\n" + planszaGraczaStrzaly.substring(108,141)+ " 4" +"\r\n" + "----------------------------------" +"\r\n" + planszaGraczaStrzaly.substring(144,177)+ " 5" + "\r\n" + "----------------------------------" + "\r\n" + planszaGraczaStrzaly.substring(180,213)+ " 6" +"\r\n" + "----------------------------------" + "\r\n" + planszaGraczaStrzaly.substring(216,249)+ " 7" +"\r\n" + "----------------------------------" + "\r\n" + planszaGraczaStrzaly.substring(252,285)+ " 8" +"\r\n" + "----------------------------------" + "\r\n" + planszaGraczaStrzaly.substring(288,321) + " 9\u001B[0m" + "\r\n");
    }
    public void devAktualizujPlanszeBota(){
        StringBuilder planszaBotaLodkistrbld = new StringBuilder(planszaBotaLodki);
        for(int i = 0;i<81;i++){
            if(planszaBotaLodkiarray[i].shot == true){
                planszaBotaLodkistrbld.setCharAt( i * 4, '·');
            }
            if(planszaBotaLodkiarray[i].containsShip == true){
                planszaBotaLodkistrbld.setCharAt( i * 4, '■');
            }
            if(planszaBotaLodkiarray[i].sunken == true){
                planszaBotaLodkistrbld.setCharAt( i * 4, 'X');
            }
            if(planszaBotaLodkiarray[i].containsShip == false && planszaBotaLodkiarray[i].sunken == true){
                planszaBotaLodkistrbld.setCharAt( i * 4, 'Ø');
            }
        }
        planszaBotaLodki = planszaBotaLodkistrbld.toString();

        StringBuilder planszaBotaStrzalystrbld = new StringBuilder(planszaBotaStrzaly);
        for(int i = 0;i<81;i++){
            if(planszaBotaStrzalyarray[i].shot == true){
                planszaBotaStrzalystrbld.setCharAt( i * 4, '·');
            }
            if(planszaBotaStrzalyarray[i].sunken == true){
                planszaBotaStrzalystrbld.setCharAt( i * 4, 'X');
            }
            if(planszaBotaStrzalyarray[i].containsShip == false && planszaBotaStrzalyarray[i].sunken == true){
                planszaBotaStrzalystrbld.setCharAt( i * 4, 'Ø');
            }
        }
        planszaBotaStrzaly = planszaBotaStrzalystrbld.toString();

        //System.out.println("            Bota Board");
        //System.out.println("            -----------");
        //System.out.println(planszaBotaLodki.substring(0,33) + "\r\n" + "----------------------------------" + "\r\n" + planszaBotaLodki.substring(36,69)+ "\r\n" + "----------------------------------" + "\r\n" + planszaBotaLodki.substring(72,105)+ "\r\n" + "----------------------------------" + "\r\n" + planszaBotaLodki.substring(108,141)+ "\r\n" + "----------------------------------" + "\r\n" + planszaBotaLodki.substring(144,177)+ "\r\n" + "----------------------------------" + "\r\n" + planszaBotaLodki.substring(180,213)+ "\r\n" + "----------------------------------" + "\r\n" + planszaBotaLodki.substring(216,249)+ "\r\n" + "----------------------------------" + "\r\n" + planszaBotaLodki.substring(252,285)+ "\r\n" + "----------------------------------" + "\r\n" + planszaBotaLodki.substring(288,321) + "\r\n");
        //System.out.println("            Enemy(Player) Board");
        //System.out.println("            -----------");
        //System.out.println(" A  B   C   D   E   F   G   H   I");
        //System.out.println(planszaBotaStrzaly.substring(0,33) + " 1" + "\r\n" + "----------------------------------" + "\r\n" + planszaBotaStrzaly.substring(36,69)+ " 2" +"\r\n" + "----------------------------------" + "\r\n" + planszaBotaStrzaly.substring(72,105)+ " 3" +"\r\n" + "----------------------------------" + "\r\n" + planszaBotaStrzaly.substring(108,141)+ " 4" +"\r\n" + "----------------------------------" +"\r\n" + planszaBotaStrzaly.substring(144,177)+ " 5" + "\r\n" + "----------------------------------" + "\r\n" + planszaBotaStrzaly.substring(180,213)+ " 6" +"\r\n" + "----------------------------------" + "\r\n" + planszaBotaStrzaly.substring(216,249)+ " 7" +"\r\n" + "----------------------------------" + "\r\n" + planszaBotaStrzaly.substring(252,285)+ " 8" +"\r\n" + "----------------------------------" + "\r\n" + planszaBotaStrzaly.substring(288,321) + " 9" + "\r\n");
    }
    public void poczatkowelodzie(){
        kladzLodzie(5, "Carrier");
        if(GraczashipsCompleted == true){
            return;
        }
        kladzLodzie(4, "Battleship");
        if(GraczashipsCompleted == true){
            return;
        }
        kladzLodzie(3, "Cruiser");
        if(GraczashipsCompleted == true){
            return;
        }
        kladzLodzie(3, "Submarine");
        if(GraczashipsCompleted == true){
            return;
        }
        kladzLodzie(2, "Destroyer");
        GraczashipsCompleted = true;
        System.out.println("Successfully added all ships!");
    }
    public void kladzLodzie(int shipLength, String shipName){
        System.out.println("Please input grid locations for " + shipName + " (" + shipLength +" long) (e.g. A1)");
        aktualizujPlansze();
        String originalBoatOrientation = "";
        String currentBoatOrientation = "";
        int convertedLocation = convertGridToNumLocation(reader.next());
        if(planszaGraczaLodkiarray[convertedLocation].containsShip == false){
            planszaGraczaLodkiarray[convertedLocation].containsShip = true;
            planszaGraczaLodkiarray[convertedLocation].shipName = shipName;
            System.out.println("Ship part Successfully placed!" + "\r\n");
        }
        else{
            System.out.println("Ship placement at this location already exists!" + "\r\n" + "Please try again");
            for (int c = 0; c < planszaGraczaLodkiarray.length; c++) {
                planszaGraczaLodkiarray[c] = new tile(false,false,false,"");
              }
              planszaGraczaLodki = planszaCzysta;
              poczatkowelodzie();
              return;
        }
        aktualizujPlansze();
        for(int i = shipLength - 1;i>0;i--){ //powtarza na kazda czesc lodzi i sprawdza czy jest obok ostatniej czesci lodzi
            System.out.println(shipName + " placements left:" + i);
            int ostatniaPozycja = convertedLocation;
            boolean adjacent = false;
            convertedLocation = convertGridToNumLocation(reader.next());
            if(convertedLocation == 9 || convertedLocation == 18 || convertedLocation == 27 || convertedLocation == 36 || convertedLocation == 45 || convertedLocation == 54 || convertedLocation == 63 || convertedLocation == 72){
                if(convertedLocation - 9 == ostatniaPozycja || convertedLocation + 9 == ostatniaPozycja){
                    adjacent = true;
                    if(originalBoatOrientation.equals("")){
                    originalBoatOrientation = "vert";
                    currentBoatOrientation = "vert";
                    }
                    else{
                        currentBoatOrientation = "vert";
                    }
                }
                if(convertedLocation + 1 == ostatniaPozycja){
                    adjacent = true;
                    if(originalBoatOrientation.equals("")){
                    originalBoatOrientation = "hor";
                    currentBoatOrientation = "hor";
                    }
                    else{
                        currentBoatOrientation = "hor";
                    }
                }
            }
            else if(convertedLocation == 8 || convertedLocation == 17 || convertedLocation == 26 || convertedLocation == 35 || convertedLocation == 44 || convertedLocation == 53 || convertedLocation == 62 || convertedLocation == 71){
                if(convertedLocation - 9 == ostatniaPozycja || convertedLocation + 9 == ostatniaPozycja){
                    adjacent = true;
                    if(originalBoatOrientation.equals("")){
                    originalBoatOrientation = "vert";
                    currentBoatOrientation = "vert";
                    }
                    else{
                        currentBoatOrientation = "vert";
                    }
                }
                if(convertedLocation - 1 == ostatniaPozycja){
                    adjacent = true;
                    if(originalBoatOrientation.equals("")){
                    originalBoatOrientation = "hor";
                    currentBoatOrientation = "hor";
                    }
                    else{
                        currentBoatOrientation = "hor";
                    }
                }
            }
            else{
                if(convertedLocation - 9 == ostatniaPozycja || convertedLocation + 9 == ostatniaPozycja){
                    adjacent = true;
                    if(originalBoatOrientation.equals("")){
                    originalBoatOrientation = "vert";
                    currentBoatOrientation = "vert";
                    }
                    else{
                        currentBoatOrientation = "vert";
                    }
                }
                if(convertedLocation - 1 == ostatniaPozycja || convertedLocation + 1 == ostatniaPozycja){
                    adjacent = true;
                    if(originalBoatOrientation.equals("")){
                    originalBoatOrientation = "hor";
                    currentBoatOrientation = "hor";
                    }
                    else{
                        currentBoatOrientation = "hor";
                    }
                }
            }
            if(planszaGraczaLodkiarray[convertedLocation].containsShip == false && adjacent == true && originalBoatOrientation.equals(currentBoatOrientation)){
                planszaGraczaLodkiarray[convertedLocation].containsShip = true;
                planszaGraczaLodkiarray[convertedLocation].shipName = shipName;
                System.out.println("Ship part Successfully placed!" + "\r\n");
            }
            else{
                if(adjacent == true){
                    if(planszaGraczaLodkiarray[convertedLocation].containsShip == true){
                        System.out.println("Ship placement at this location already exists!" + "\r\n" + "Please try again");
                    }
                    else{
                        System.out.println("Ship placement needs to be a straight line!" + "\r\n" + "Please try again");
                    }
                }
                else{
                    System.out.println("Ship placement needs to be adjacent!" + "\r\n" + "Please try again");
                }
                for (int b = 0; b < planszaGraczaLodkiarray.length; b++) {
                    planszaGraczaLodkiarray[b] = new tile(false,false,false,"");
                  }
                  planszaGraczaLodki = planszaCzysta;
                  poczatkowelodzie();
                  return;
            }
            aktualizujPlansze();
        }
        System.out.println(shipName + " successfully added!");
    }
    public void botKladzLodzie(){
        for(int i = 0;i<ships.length;i++){
            Boolean boatConfirmation = false;
            while(boatConfirmation == false){
                for(int a = 0;a<81;a++){
                    ghostPlanszaBotaLodkiarray[a].containsShip = planszaBotaLodkiarray[a].containsShip;
                    ghostPlanszaBotaLodkiarray[a].shipName = planszaBotaLodkiarray[a].shipName;               // kopiuje jeden array do drugiego bez problemow
                }
                boatConfirmation = botKladzLodziePojedynczo(i, ships[i].shipName);
            }
                for(int a = 0;a<81;a++){
                    planszaBotaLodkiarray[a].containsShip = ghostPlanszaBotaLodkiarray[a].containsShip;
                    planszaBotaLodkiarray[a].shipName = ghostPlanszaBotaLodkiarray[a].shipName;              // kopiuje jeden array do drugiego bez problemow
                }
        }
        devAktualizujPlanszeBota();
    }
    public boolean botKladzLodziePojedynczo(int i, String shipName){
        Random random = new Random();
        int randomPosition = random.nextInt(79); // nie ma sensu generowac ostatniego miejsca bo odrazu odrzuci opcje bo nie mozna polozyc lodzi w 1 kwadraciku
        while(ghostPlanszaBotaLodkiarray[randomPosition].containsShip == true){
            randomPosition = random.nextInt(79);
        }
        ghostPlanszaBotaLodkiarray[randomPosition].containsShip = true;
        ghostPlanszaBotaLodkiarray[randomPosition].shipName = shipName;
        boolean vertOrHor = random.nextBoolean();
        for(int a = ships[i].length - 1;a>0;a--){
            try {
                if(vertOrHor == true){ // vertical
                    if(ghostPlanszaBotaLodkiarray[randomPosition + 9].containsShip == true){
                        return(false);
                    }
                    //dalszy kod tylko jest uruchamiany kiedy polozenie czesc lodzi bedzie dozwolone
                    randomPosition += 9;
                    ghostPlanszaBotaLodkiarray[randomPosition].containsShip = true;
                    ghostPlanszaBotaLodkiarray[randomPosition].shipName = shipName;
                }
                else{ // horizontal
                    if(ghostPlanszaBotaLodkiarray[randomPosition + 1].containsShip == true){
                        return(false);
                    }
                    else if(randomPosition + 1 == 9 || randomPosition + 1 == 18 || randomPosition + 1 == 27 || randomPosition + 1 == 36 || randomPosition + 1 == 45 || randomPosition + 1 == 54 || randomPosition + 1 == 63 || randomPosition + 1 == 72){
                        return(false);
                    }
                    //dalszy kod tylko jest uruchamiany kiedy polozenie czesc lodzi bedzie dozwolone
                    randomPosition += 1;
                    ghostPlanszaBotaLodkiarray[randomPosition].containsShip = true;
                    ghostPlanszaBotaLodkiarray[randomPosition].shipName = shipName;
                }
            } catch (Exception e) {
                return(false);
            }
        }
       return(true);
    }
    public int convertGridToNumLocation(String gridInput){
        boolean valid = false;
            StringBuilder gridInputstrbld = new StringBuilder(gridInput.toUpperCase());
            if(gridInputstrbld.substring(0, 1).equals("A")){
                gridInputstrbld.setCharAt(0, '0');
                valid = true;
            }
            else if(gridInputstrbld.substring(0, 1).equals("B")){
                gridInputstrbld.setCharAt(0, '1');
                valid = true;
            }
            else if(gridInputstrbld.substring(0, 1).equals("C")){
                gridInputstrbld.setCharAt(0, '2');
                valid = true;
            }
            else if(gridInputstrbld.substring(0, 1).equals("D")){
                gridInputstrbld.setCharAt(0, '3');
                valid = true;
            }
            else if(gridInputstrbld.substring(0, 1).equals("E")){
                gridInputstrbld.setCharAt(0, '4');
                valid = true;
            }
            else if(gridInputstrbld.substring(0, 1).equals("F")){
                gridInputstrbld.setCharAt(0, '5');
                valid = true;
            }
            else if(gridInputstrbld.substring(0, 1).equals("G")){
                gridInputstrbld.setCharAt(0, '6');
                valid = true;
            }
            else if(gridInputstrbld.substring(0, 1).equals("H")){
                gridInputstrbld.setCharAt(0, '7');
                valid = true;
            }
            else if(gridInputstrbld.substring(0, 1).equals("I")){
                gridInputstrbld.setCharAt(0, '8');
                valid = true;
            }
            if(gridInputstrbld.length() < 2){
                valid = false;
            }
            if(valid == false){
                System.out.println("Invalid input, please try again");
                int i = convertGridToNumLocation(reader.next());
                return(i);
            }
            if(gridInputstrbld.substring(1,2).equals("1") || gridInputstrbld.substring(1,2).equals("2") || gridInputstrbld.substring(1,2).equals("3") || gridInputstrbld.substring(1,2).equals("4") || gridInputstrbld.substring(1,2).equals("5") || gridInputstrbld.substring(1,2).equals("6") || gridInputstrbld.substring(1,2).equals("7") || gridInputstrbld.substring(1,2).equals("8") || gridInputstrbld.substring(1,2).equals("9") ){
                gridInputstrbld.setCharAt(1, (String.valueOf(Integer.parseInt(gridInputstrbld.substring(1, 2)) - 1)).charAt(0));
            }
            else{
                System.out.println("Invalid input, please try again");
                int i = convertGridToNumLocation(reader.next());
                return(i);
            }
            int a = Integer.parseInt(gridInputstrbld.substring(0,1));
            int b = Integer.parseInt(gridInputstrbld.substring(1,2));
            b = b * 9;
        return(a + b);
    }
    public void graczStrzela(){
        System.out.print("Enemy ships left to sink: ");
        for(int z = 0;z < botaLodkiNieZatopione.size();z++){
            if(botaLodkiNieZatopione.get(z).equals("Carrier")){
                System.out.print("Carrier (5 long)");
            }
            else if(botaLodkiNieZatopione.get(z).equals("Battleship")){
                System.out.print("Battleship (4 long)");
            }
            else if(botaLodkiNieZatopione.get(z).equals("Cruiser")){
                System.out.print("Cruiser (3 long)");
            }
            else if(botaLodkiNieZatopione.get(z).equals("Submarine")){
                System.out.print("Submarine (3 long)");
            }
            else if(botaLodkiNieZatopione.get(z).equals("Destroyer")){
                System.out.print("Destroyer (2 long)");
            }
            if(z >= botaLodkiNieZatopione.size() - 1){
                continue;
            }
            System.out.print(", ");
        }
        System.out.println("\r\nInput target grid location (e.g. a1)");
        int arrayPosition = -1;
        boolean duplicateLocation = true;
        while(duplicateLocation == true){
            arrayPosition = convertGridToNumLocation(reader.next());
            if(!graczaStrzaly.contains(arrayPosition)){
                duplicateLocation = false;
                graczaStrzaly.add(arrayPosition);
            }
            else{
                System.out.println("You have already shot to this location! Please choose another");
            }
        }
        if(planszaBotaLodkiarray[arrayPosition].containsShip == false && planszaBotaLodkiarray[arrayPosition].sunken == false){
            planszaBotaLodkiarray[arrayPosition].shot = true;
            planszaGraczaStrzalyarray[arrayPosition].shot = true;
            messageToWrite = "You Missed";
        }
        else if(planszaBotaLodkiarray[arrayPosition].containsShip == true && planszaBotaLodkiarray[arrayPosition].sunken == false){
            planszaBotaLodkiarray[arrayPosition].sunken = true;
            planszaGraczaStrzalyarray[arrayPosition].containsShip = true;
            planszaGraczaStrzalyarray[arrayPosition].sunken = true;
            messageToWrite = "You Hit";
        }
        for(int z = 0;z<ships.length;z++){
            boolean allOfShipNotSunken = false;
            for(int i = 0;i<81;i++){
                if(planszaBotaLodkiarray[i].shipName.equals(ships[z].shipName) && planszaBotaLodkiarray[i].sunken == false){
                    allOfShipNotSunken = true;
                }
            }
            if(allOfShipNotSunken == false){
                for(int a = 0;a<81;a++){
                    if(planszaBotaLodkiarray[a].shipName.equals(ships[z].shipName)){
                        planszaBotaLodkiarray[a].containsShip = false;
                        planszaGraczaStrzalyarray[a].containsShip = false;
                    }
                }
                if(botaLodkiNieZatopione.contains(ships[z].shipName)){
                    messageToWrite += "\r\nEnemy " + ships[z].shipName + " sunken!";
                    botaLodkiNieZatopione.remove(ships[z].shipName);
                }
            }
        }
    }
    public void botStrzela(){
        lastBotMove = arrayPosition;
        beenAt0 = false;
        botShootMode0Finished = false;
        botShootMode1Finished = false;
        botShootMode2Finished = false;
        while(botShootMode0Finished == false && lastBotMove == arrayPosition){
        	if(botShootMode == 0) {
                beenAt0 = true;
                rotated = false;
                if(lastBotMoveWasHit == true){
                    firstBotSuccessfullHit = arrayPosition;
                    botShootMode = 1;
                    botShootMode1list.clear();
                    botShootMode1list.add(1);
                    botShootMode1list.add(9);
                    botShootMode1list.add(-1);
                    botShootMode1list.add(-9);
                }
                else{
                	botShootMode0();
                }
        	}
            else{
                botShootMode0Finished = true;
            }
        }
        botShootMode0Finished = false;
        botShootMode1Finished = false;
        botShootMode2Finished = false;
        while(botShootMode1Finished == false && lastBotMove == arrayPosition){
    	    if(botShootMode == 1) {
    	        botShootMode1();
        	}
            else{
                botShootMode1Finished = true;
            }
        }
        botShootMode0Finished = false;
        botShootMode1Finished = false;
        botShootMode2Finished = false;
    	while(botShootMode2Finished == false && lastBotMove == arrayPosition){
            if(botShootMode == 2){
                botShootMode2();
            }
            else{
                botShootMode2Finished = true;
            }
    	}
        botaStrzaly.add(arrayPosition);
        botShootCheckerboard.remove(Integer.valueOf(arrayPosition));
        if(planszaGraczaLodkiarray[arrayPosition].containsShip == false && planszaGraczaLodkiarray[arrayPosition].sunken == false){
            planszaGraczaLodkiarray[arrayPosition].shot = true;
            planszaBotaStrzalyarray[arrayPosition].shot = true;
            messageToWrite = "Bot Missed";
            lastBotMoveWasHit = false;
        }
        else if(planszaGraczaLodkiarray[arrayPosition].containsShip == true && planszaGraczaLodkiarray[arrayPosition].sunken == false){
            planszaGraczaLodkiarray[arrayPosition].sunken = true;
            planszaBotaStrzalyarray[arrayPosition].containsShip = true;
            planszaBotaStrzalyarray[arrayPosition].sunken = true;
            messageToWrite = "Bot Hit";
            lastBotMoveWasHit = true;
        }
        for(int z = 0;z<ships.length;z++){
            boolean allOfShipNotSunken = false;
            for(int i = 0;i<81;i++){
                if(planszaGraczaLodkiarray[i].shipName.equals(ships[z].shipName) && planszaGraczaLodkiarray[i].sunken == false){
                    allOfShipNotSunken = true;
                }
            }
            if(allOfShipNotSunken == false){
                for(int a = 0;a<81;a++){
                    if(planszaGraczaLodkiarray[a].shipName.equals(ships[z].shipName)){
                        planszaGraczaLodkiarray[a].containsShip = false;
                        planszaBotaStrzalyarray[a].containsShip = false;
                    }
                }
                if(graczaLodkiNieZatopione.contains(ships[z].shipName)){
                    messageToWrite += "\r\nBot sank your " + ships[z].shipName;
                    graczaLodkiNieZatopione.remove(ships[z].shipName);
                    botShootMode = 0;
                    lastBotMoveWasHit = false;
                }
            }
        }
    }
    public void botShootMode0(){
	    while(arrayPosition == lastBotMove){
	    	int i = 0;
      	    for(i = 0;i<81;i++){
                if(planszaBotaStrzalyarray[i].sunken == true && planszaBotaStrzalyarray[i].containsShip == true && !probujemyDoOkola.contains(i)){
                    firstBotSuccessfullHit = i;
                    botShootMode = 1;
                    botShootMode1list.clear();
                    botShootMode1list.add(1);
                    botShootMode1list.add(9);
                    botShootMode1list.add(-1);
                    botShootMode1list.add(-9);
                    arrayPosition = i;
                    probujemyDoOkola.add(i);
                    lastBotMove = arrayPosition;
                    break;
                }
            }
    	    if(i == 81) {
                botShootMode = 0;
    	        boolean duplicateLocation = true;
    	        Random randomLocation = new Random();
                if(botShootCheckerboard.size() < 1){
                    for(int b = 0;b < botShootCheckerboardBackup.size();b++){
                        if((botShootCheckerboardBackup.get(b) + 1) % 9 == 0){
                            botShootCheckerboardBackup.set(b, botShootCheckerboardBackup.get(b) - 9);
                        }
                        botShootCheckerboardBackup.set(b, botShootCheckerboardBackup.get(b) + 1);
                    }
                    for (Integer integer : botShootCheckerboardBackup) {
                        botShootCheckerboard.add(integer);
                    }
                }
    	        while(duplicateLocation == true || !botShootCheckerboard.contains(arrayPosition)){
    	            arrayPosition = randomLocation.nextInt(81);
    	            if(!botaStrzaly.contains(arrayPosition)){
    	                duplicateLocation = false;
    	            }
                    else{
                        duplicateLocation = true;
                    }
    	        }
    	    }
    	    while(arrayPosition == lastBotMove){
                if(arrayPosition % 9 == 0){
                    botShootMode1list.remove(Integer.valueOf(-1));
                }
                if(arrayPosition >=0 && arrayPosition <=8){
                    botShootMode1list.remove(Integer.valueOf(-9));
                }
                if((arrayPosition+1) % 9 == 0){
                    botShootMode1list.remove(Integer.valueOf(1));
                }
                if(arrayPosition >=72 && arrayPosition <=80){
                    botShootMode1list.remove(Integer.valueOf(9));
                }
                if(botShootMode1list.size() > 0){
                    if(botaStrzaly.contains(firstBotSuccessfullHit + botShootMode1list.get(0)) == true){
                        botShootMode1list.remove(0);
                    }
                    else{
                    	arrayPosition = firstBotSuccessfullHit + botShootMode1list.get(0);
                        if(botShootMode1list.size() > 1){
                            probujemyDoOkola.remove(Integer.valueOf(i));
                        }
                    }
                }
                else {
                	break;
                }
    	    }
	    }
    }
    public void botShootMode1(){
        if(arrayPosition % 9 == 0){
            botShootMode1list.remove(Integer.valueOf(-1));
        }
        if(arrayPosition >=0 && arrayPosition <=8){
            botShootMode1list.remove(Integer.valueOf(-9));
        }
        if((arrayPosition+1) % 9 == 0){
            botShootMode1list.remove(Integer.valueOf(1));
        }
        if(arrayPosition >=72 && arrayPosition <=80){
            botShootMode1list.remove(Integer.valueOf(9));
        }
    	if(lastBotMoveWasHit == true && beenAt0 == false){
            if(firstBotSuccessfullHit - 1 == arrayPosition){
                botBoatShootDirection = -1;
            }
            else if(firstBotSuccessfullHit + 1 == arrayPosition){
                botBoatShootDirection = 1;
            }
            else if(firstBotSuccessfullHit - 9 == arrayPosition){
                botBoatShootDirection = -9;
            }
            else if(firstBotSuccessfullHit + 9 == arrayPosition){
                botBoatShootDirection = 9;
            }
            botShootMode = 2;
        }
        else{
            if(botShootMode1list.size() > 0){
                if(botaStrzaly.contains(firstBotSuccessfullHit + botShootMode1list.get(0)) == true){
                    botShootMode1list.remove(0);
                }
                else{
                	arrayPosition = firstBotSuccessfullHit + botShootMode1list.get(0);
                }
            }
            else{
                botShootMode0();
            }
        }
    }
    public void botShootMode2(){
    	if(lastBotMoveWasHit == false){
            if(rotated == true){
                botShootMode0();
            }
            else{
                rotated = true;
                botBoatShootDirection = botBoatShootDirection * -1;
                if((firstBotSuccessfullHit + botBoatShootDirection > 80 || firstBotSuccessfullHit + botBoatShootDirection < 0) || botaStrzaly.contains(firstBotSuccessfullHit + botBoatShootDirection) == true || ((firstBotSuccessfullHit % 9 == 0) && botBoatShootDirection == -1) || ((firstBotSuccessfullHit + 1) % 9 == 0) && botBoatShootDirection == 1){
                    botShootMode0();
                }
                else{
                    arrayPosition = firstBotSuccessfullHit + botBoatShootDirection;
                }
            }
        }
        else{
            if((arrayPosition + botBoatShootDirection > 80 || arrayPosition + botBoatShootDirection < 0 || botaStrzaly.contains(arrayPosition + botBoatShootDirection) == true) || ((arrayPosition % 9 == 0) && botBoatShootDirection == -1) || ((arrayPosition + 1) % 9 == 0) && botBoatShootDirection == 1){
            	if(rotated == true){
                    botShootMode0();
                }
                else{
                    rotated = true;
                    botBoatShootDirection = botBoatShootDirection * -1;
                    if((firstBotSuccessfullHit + botBoatShootDirection > 80 || firstBotSuccessfullHit + botBoatShootDirection < 0) || botaStrzaly.contains(firstBotSuccessfullHit + botBoatShootDirection) == true || ((firstBotSuccessfullHit % 9 == 0) && botBoatShootDirection == -1) || ((firstBotSuccessfullHit + 1) % 9 == 0) && botBoatShootDirection == 1){
                        botShootMode0();
                    }
                    else{
                        arrayPosition = firstBotSuccessfullHit + botBoatShootDirection;
                    }
                }
            }
            else{
                arrayPosition += botBoatShootDirection;
            }
        }
    }
}