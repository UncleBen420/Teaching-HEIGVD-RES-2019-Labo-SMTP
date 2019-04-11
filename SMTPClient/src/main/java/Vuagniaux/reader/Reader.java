package Vuagniaux.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Lit un fichier.
 * Propose une methode lisant un fichier et renvoyant les lignes de ce fichiers,
 * et chacune de ces lignes est splitee suivant un separateur donne.
 */
public class Reader {
    private String delimiter;
    private String fileName;
    private boolean readFirstLine;

    /**
     * Construit un Reader pour un fichier. Le separateur par defaut des elements
     * est le retour a la ligne. La premiere ligne est lue par defaut.
     *
     * @param filename le nom du fichier a lire
     */
    public Reader(String filename) {
        this(filename, System.lineSeparator(), true);
    }

    /**
     * Construit un Reader pour un fichier. La premiere ligne est lue par defaut.
     *
     * @param fileName  le nom du fichier a lire
     * @param delimiter le separateur pour splitter les lignes
     */
    public Reader(String fileName, String delimiter) {
        this(fileName, delimiter, true);
    }

    /**
     * Construit un Reader pour un fichier. Le separateur par defaut des
     * elements d'une ligne est le retour a la ligne.
     *
     * @param fileName      le nom du fichier a lire
     * @param readFirstLine si la premiere ligne doit etre lue ou pas
     */
    public Reader(String fileName, boolean readFirstLine) {
        this(fileName, System.lineSeparator(), readFirstLine);
    }

    /**
     * Construit un Reader pour un fichier.
     *
     * @param fileName      le nom du fichier a lire
     * @param delimiter     le separateur pour splitter les lignes
     * @param readFirstLine si la premiere ligne doit etre lue ou pas
     */
    public Reader(String fileName, String delimiter, boolean readFirstLine) {
        this.delimiter = delimiter;
        this.fileName = fileName;
        this.readFirstLine = readFirstLine;
    }

    /**
     * @param fileName le nom de fichier a changer
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Lit le fichier en utilisant un buffer. A utiliser pour un csv-like.
     * Separe les lignes.
     * La lecture a l'aide d'un buffer est inspiree de ce lien :
     * https://www.baeldung.com/java-csv-file-array
     *
     * @return Une liste (ensemble des lignes) de listes de string
     * (une ligne potientiellement splittee en plusieurs string).
     * Si on ne splite pas la ligne (on lit un fichier normal, pas un csv)
     * on aura donc une seule string par sous-liste.
     */
    public List<List<String>> splittedLinesBufferedReading() {
        List<List<String>> readData = new ArrayList<>();

        try {
            String line;
            // On ouvre le fichier
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            // String[] pour pouvoir utiliser split
            String[] splittedLine;

            /*
             * On saute la premiere ligne si on ne la veut pas
             * (par exemple si on a un fichier avec des entetes de colonnes)
             */
            if (!readFirstLine) {
                reader.readLine();
            }

            // On lit une ligne a la fois, on la split, on l'ajoute aux autres lignes
            while ((line = reader.readLine()) != null) {
                splittedLine = line.split(delimiter);
                readData.add(Arrays.asList(splittedLine));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readData;
    }

    /**
     * Lit le fichier en utilisant un buffer. A utiliser si on ne veut pas
     * splitter les lignes lues.
     *
     *  @return Les lignes du fichier lu.
     */
    public List<String> lineBufferedReading() {
        List<String> readData = new ArrayList<>();

        try {
            String line;

            // On ouvre le fichier
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            /*
             * On saute la premiere ligne si on ne la veut pas
             * (par exemple si on a un fichier avec des entetes de colonnes)
             */
            if (!readFirstLine) {
                reader.readLine();
            }

            // On lit une ligne a la fois et on l'ajoute aux autres lignes
            while ((line = reader.readLine()) != null) {
                readData.add(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readData;
    }

    /**
     * Lit le fichier en utilisant un buffer. Lit le fichier comme une seule string.
     * A utiliser si on ne veut pas separer les lignes.
     *
     * @return Le fichier lu comme une seule string
     */
    public String bufferedReading() {
        StringBuilder readData = new StringBuilder();

        try {
            String line;

            // On ouvre le fichier
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            /*
             * On saute la premiere ligne si on ne la veut pas
             * (par exemple si on a un fichier avec des entetes de colonnes)
             */
            if (!readFirstLine) {
                reader.readLine();
            }

            // On lit une ligne a la fois et on l'ajoute aux autres lignes
            while ((line = reader.readLine()) != null) {
                readData.append(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readData.toString();
    }
    
    /**
     * Permet de lire un fichier de proprietes. La lecture est bufferisee.
     *
     * @return Un objet contenant les proprietes
     */
    public Properties readProperties() {
        Properties properties = new Properties();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("config.properties"));

            // On charge les proprietes
            properties.load(reader);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return properties;
    }
}
