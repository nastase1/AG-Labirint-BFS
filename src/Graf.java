import java.util.*;

public class Graf {
    private List<Nod> noduri; // Lista de noduri din graf
    private List<Arc> arce; // Lista de arce din graf

    public Graf(Labirint labirint) {
        noduri = new ArrayList<>();
        arce = new ArrayList<>();
        construiesteGraf(labirint);
    }

    private void construiesteGraf(Labirint labirint) {
        int[][] matrice = labirint.getMatriceLabirint();
        int id = 0;

        // Creează nodurile pentru toate drumurile (1 în matrice)
        for (int i = 0; i < labirint.getInaltime(); i++) {
            for (int j = 0; j < labirint.getLatime(); j++) {
                if (matrice[i][j] == 1) {  // Verificăm dacă este drum
                    Nod nod = new Nod(j, i, id++); // Creăm nodul la coordonatele (j, i)
                    noduri.add(nod); // Adăugăm nodul în lista de noduri
                    System.out.println("Nod creat la coordonatele (" + j + ", " + i + ")");
                }
            }
        }

        // Creează arce pentru drumurile accesibile între noduri vecine
        for (Nod nod : noduri) {
            int x = nod.getCoordX();
            int y = nod.getCoordY();

            // Vecini: sus, jos, stânga, dreapta
            adaugaArcDacaEsteDrum(nod, x, y - 1, labirint); // sus
            adaugaArcDacaEsteDrum(nod, x, y + 1, labirint); // jos
            adaugaArcDacaEsteDrum(nod, x - 1, y, labirint); // stânga
            adaugaArcDacaEsteDrum(nod, x + 1, y, labirint); // dreapta
        }
    }


    // Adaugă un arc între noduri dacă există drum
    private void adaugaArcDacaEsteDrum(Nod nodStart, int x, int y, Labirint labirint) {
        if (labirint.esteDrum(x, y)) {
            Nod nodVecin = getNodAt(x, y);
            if (nodVecin != null) {
                Arc arc = new Arc(nodStart, nodVecin, 1);
                arce.add(arc);
                nodStart.adaugaVecin(nodVecin);
            }
        }
    }

    // Găsește un nod la coordonatele specificate
    public Nod getNodAt(int x, int y) {
        for (Nod nod : noduri) {
            if (nod.getCoordX() == x && nod.getCoordY() == y) {
                return nod;
            }
        }
        System.err.println("Nu există un nod la coordonatele (" + x + ", " + y + ").");
        return null;
    }


    // Parcurgerea în lățime pentru a găsi drumuri scurte din nodul start
    public Map<Nod, Integer> parcurgereLatime(Nod start) {
        Map<Nod, Integer> distante = new HashMap<>();
        Queue<Nod> coada = new LinkedList<>();

        if (start == null) {
            System.err.println("Nodul de start este null.");
            return distante; // Returnăm un map gol
        }

        // Inițializăm distanțele
        for (Nod nod : noduri) {
            distante.put(nod, Integer.MAX_VALUE);
        }
        distante.put(start, 0);
        coada.add(start);

        while (!coada.isEmpty()) {
            Nod nodCurent = coada.poll();
            if (nodCurent == null) continue; // Verificare de siguranță

            int distCurenta = distante.get(nodCurent);

            for (Nod vecin : nodCurent.getVecini()) {
                if (distCurenta + 1 < distante.get(vecin)) {
                    distante.put(vecin, distCurenta + 1);
                    coada.add(vecin);
                }
            }
        }
        return distante;
    }


    public List<Nod> getNoduri() {
        return noduri;
    }

    public List<Arc> getArce() {
        return arce;
    }
}
