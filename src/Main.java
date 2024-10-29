import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class Main {

    private static void initUI() {
        JFrame f = new JFrame("Labirint");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inițializează Labirint și PanouLabirint
        Labirint labirint = new Labirint("fisier.txt");
        Graf graf = new Graf(labirint);

        Nod startNod = graf.getNodAt(5,0);
        if (startNod == null) {
            System.err.println("Nodul de start nu a fost găsit în labirint la coordonatele (1, 2).");
            return;
        }

        PanouLabirint panouLabirint = new PanouLabirint(labirint,startNod);

        // Adaugă PanouLabirint în JFrame
        f.add(panouLabirint);

        f.setSize(600, 500);

        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initUI();
            }
        });
    }
}