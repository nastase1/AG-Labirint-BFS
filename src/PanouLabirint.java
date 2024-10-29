import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanouLabirint extends JPanel {
    private Labirint labirint;
    private Graf graf;
    private Nod startNod;
    private List<List<Nod>> drumuriVerzi; // Lista de drumuri către ieșiri
    private List<Nod> noduriRosii; // Nodurile care nu sunt accesibile
    private int currentPathIndex = 0; // Indexul pentru desenarea drumului curent

    // Constructorul primește un obiect Labirint și un nod de start
    public PanouLabirint(Labirint labirint, Nod startNod) {
        this.labirint = labirint;
        this.startNod = startNod;
        this.graf = new Graf(labirint); // Inițializăm graful pe baza labirintului
        drumuriVerzi = new ArrayList<>();
        noduriRosii = new ArrayList<>();

        // Găsim drumurile verzi (către ieșiri) și nodurile roșii
        calculeazaDrumuriSiNoduri();

        // Setăm un buton pentru a colora următorul drum
        JButton nextPathButton = new JButton("Next");
        nextPathButton.addActionListener(e -> {
            if (currentPathIndex < drumuriVerzi.size() - 1) {
                currentPathIndex++;
                repaint();
            }
        });

        // Adăugăm butonul în panou
        this.setLayout(new BorderLayout());
        this.add(nextPathButton, BorderLayout.SOUTH);
    }

    // Suprascrierea metodei paintComponent pentru a desena labirintul și drumurile
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (labirint != null) {
            labirint.afiseazaLabirint(g); // Desenăm labirintul
        }

        g.setColor(Color.GREEN);
        g.fillRect(startNod.getCoordX() * labirint.getDimensiuneCelula(),
                startNod.getCoordY() * labirint.getDimensiuneCelula(),
                labirint.getDimensiuneCelula(), labirint.getDimensiuneCelula());

        // Desenăm drumurile verzi pe baza indexului curent
        if (currentPathIndex < drumuriVerzi.size()) {
            g.setColor(Color.GREEN);
            List<Nod> drumCurent = drumuriVerzi.get(currentPathIndex);
            for (Nod nod : drumCurent) {
                g.fillRect(nod.getCoordX() * labirint.getDimensiuneCelula(),
                        nod.getCoordY() * labirint.getDimensiuneCelula(),
                        labirint.getDimensiuneCelula(), labirint.getDimensiuneCelula());
            }
        }

        // Desenăm nodurile roșii (inaccesibile)
        g.setColor(Color.RED);
        for (Nod nod : noduriRosii) {
            g.fillRect(nod.getCoordX() * labirint.getDimensiuneCelula(),
                    nod.getCoordY() * labirint.getDimensiuneCelula(),
                    labirint.getDimensiuneCelula(), labirint.getDimensiuneCelula());
        }
    }

    // Metodă care calculează drumurile verzi și nodurile roșii
    private void calculeazaDrumuriSiNoduri() {
        // Găsim toate ieșirile și drumul către ele
        Map<Nod, Integer> distante = graf.parcurgereLatime(startNod);
        for (Nod nod : graf.getNoduri()) {
            if (isExit(nod) && distante.get(nod) != Integer.MAX_VALUE) {
                // Găsim drumul către ieșire
                List<Nod> drum = findPathToExit(nod, distante);
                drumuriVerzi.add(drum);
            }
        }

        // Găsim nodurile inaccesibile (roșii)
        for (Nod nod : graf.getNoduri()) {
            if (distante.get(nod) == Integer.MAX_VALUE && !drumuriVerzi.stream().flatMap(List::stream).anyMatch(n -> n.equals(nod))) {
                noduriRosii.add(nod); // Adăugăm nodurile inaccesibile
            }
        }

        // Colorăm primul drum la start
        if (!drumuriVerzi.isEmpty()) {
            currentPathIndex = 0;
        }
    }

    // Verificăm dacă nodul este o ieșire
    private boolean isExit(Nod nod) {
        // Verificăm dacă este pe marginea matricei
        return nod.getCoordX() == 0 || nod.getCoordX() == labirint.getLatime() - 1 ||
                nod.getCoordY() == 0 || nod.getCoordY() == labirint.getInaltime() - 1;
    }

    // Metodă pentru a găsi drumul de la start la ieșire
    private List<Nod> findPathToExit(Nod exit, Map<Nod, Integer> distante) {
        List<Nod> path = new ArrayList<>();
        Nod current = exit;

        // Reconstituim drumul înapoi la start
        while (current != null) {
            path.add(current);
            int currentDistance = distante.get(current);

            // Căutăm nodurile vecine care au o distanță mai mică
            for (Nod vecin : current.getVecini()) {
                if (distante.get(vecin) == currentDistance - 1) {
                    current = vecin; // Mergem la nodul vecin
                    break;
                }
            }

            // Oprire dacă am ajuns la start
            if (current.getCoordX() == startNod.getCoordX() && current.getCoordY() == startNod.getCoordY()) {
                break;
            }

            // Verificăm dacă nu avem un nod care să îndeplinească condiția
            if (currentDistance == 0) {
                break; // Dacă distanța este zero, am ajuns la start
            }
        }

        // Întoarcem drumul în ordinea corectă (de la start la ieșire)
        List<Nod> finalPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            finalPath.add(path.get(i));
        }
        return finalPath;
    }
}
