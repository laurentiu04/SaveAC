package ro.ac.castravetii;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Clasa asta este facuta special ca sa nu trebuiasca sa scriem mereu Gdx.files.functie()
 * Pentru mai multe informatii despre ce sunt chestiile de jos, uitati-va aici: <a href="https://libgdx.com/wiki/file-handling">File Handling Wiki</a>
 */
public class Utils {
    /**
     * Sincer nu stiu ce face functia asta, am vazut-o in tutorial si cica e folositoare
     * Cine stie, poate o sa o folosim
     *
     * @param filepath Calea catre fisier
     * @return un fisier cu extensia .class
     */
    public static FileHandle getClassPath(String filepath) {
        return Gdx.files.classpath(filepath);
    }

    /**
     *  Folosim asta cand vrem sa utilizam un fisier din directorul proiectului. De exemplu, daca un fisier este in folderul /assets/nume_fisier.ext
     *  il luam cu Utils.getInternalPath("nume_fisier.ext")
     * @param filepath Calea catre fisier
     * @return Un fisier din folder-ul proiectului
     */
    public static FileHandle getInternalPath(String filepath) {
        return Gdx.files.internal(filepath);
    }

    /**
     * Folosim asta cand vrem sa utilizam un fisier din directorul home al calculatorului (de obicei C:\\users\\nume_utilizator\\)
     *
     * @param filepath Calea catre fisier
     * @return  Fisier extern din C:\\users\nume_utilizator\\
     */
    public static FileHandle getExternalPath(String filepath) {
        return Gdx.files.external(filepath);
    }

    /**
     * Aproape aceeasi chestie ca si getInternalPath() pentru desktop, pe mobile e altfel dar nu ne intereseaza.
     * Probabil nu o sa folosim nici asta :)
     *
     * @param filepath Calea catre fisier
     * @return Fisier local
     */
    public static FileHandle getLocalPath(String filepath) {
        return Gdx.files.local(filepath);
    }
}
