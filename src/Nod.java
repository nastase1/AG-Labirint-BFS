import java.util.ArrayList;
import java.util.List;

public class Nod {
    private int coordX; // Coordonata X a nodului
    private int coordY; // Coordonata Y a nodului
    private int id; // ID-ul unic al nodului
    private List<Nod> vecini; // Lista de noduri vecine conectate prin drumuri accesibile

    public Nod(int coordX, int coordY, int id) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.id = id;
        this.vecini = new ArrayList<>();
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getId() {
        return id;
    }

    public void adaugaVecin(Nod vecin) {
        if (!vecini.contains(vecin)) {
            vecini.add(vecin);
        }
    }

    public List<Nod> getVecini() {
        return vecini;
    }

    @Override
    public String toString() {
        return "Nod{" + "ID=" + id + ", coordX=" + coordX + ", coordY=" + coordY + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Nod nod = (Nod) obj;
        return coordX == nod.coordX && coordY == nod.coordY && id == nod.id;
    }

    @Override
    public int hashCode() {
        int result = coordX;
        result = 31 * result + coordY;
        result = 31 * result + id;
        return result;
    }

}
