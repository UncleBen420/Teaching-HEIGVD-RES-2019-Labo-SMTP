package Vuagniaux.prankManager;

import Vuagniaux.SMTPClient.Mail;
import Vuagniaux.reader.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * Definie la structure entiere du prank.
 * Va recuperer un message par groupe, puis creer un mail par personne dans
 * ce groupe avec un message pour finalement fournir une liste
 * de mails a envoyer.
 * Puisque tout depend du fichier du configuration qui a ete construit selon
 * les desirs de l'utilisateur, cette classe s'occupe de le lire et fait
 * tout automatiquement.
 *
 * Il s'agit de la classe que devra recoder l'utilisateur pour utiliser notre
 * application dans un autre cadre que le prank
 */
public class PrankBuilder {
    private int numberOfGroups, personsPerGroup;
    private String configFile, victimsMailsFile, fromSMTP, toSMTP, fromInMail;
    private String witnesses;
    private List<Mail> forgedMails;
    private List<String> victimsMails;

    public PrankBuilder(String configFile, String victimsMailFile) {
        this.configFile = configFile;
        this.victimsMailsFile = victimsMailFile;

        // On recupere les parametres utiles du fichier de configuration
        Reader reader = new Reader(configFile);
        Properties properties = reader.readProperties();

        numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
        personsPerGroup = Integer.parseInt(properties.getProperty("personsPerGroup"));
        witnesses = properties.getProperty("witnesses");
        fromSMTP = properties.getProperty("fromSMTP");
        toSMTP = properties.getProperty("toSMTP");
        fromInMail = properties.getProperty("fromInMail");

        // On recupere les mails des victimes depuis le fichier les listant
        reader.setFileName(victimsMailsFile);
        victimsMails = reader.lineBufferedReading();

        // On teste la validite de la configuration
        try {
            testConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // On forge les mails
        forgedMails = new ArrayList<>();
        forgeMails();
    }

    // Seul methode disponible a l'utilisateur
    /**
     * Renvoie les mails forges, prets a etre envoyer
     * @return les mails forges
     */
    public List<Mail> getForgedMails() {
        return forgedMails;
    }

    /**
     * Permet de tester la coherence du fichier de configuration etabli par
     * l'utilisateur.
     * @throws Exception si une condition n'est pas respectee
     */
    private void testConfiguration() throws Exception {
        /*
         * Il faut au moins que le nombre de personnes par groupe et le nombre
         * de groupes soient en accord avec le nombre de mails dispo.
         */
        if(personsPerGroup * numberOfGroups > victimsMails.size()) {
            throw new Exception("Pas assez de mails pour le nombre de groupes et de victimes par groupe");
        }

        if(victimsMails.size() < 1) {
            throw new Exception("Aucun mail de destination specifie");
        }

        // Ajouter d'autre verifications si necessaire
    }

    /**
     * Forge les emails.
     * Dans notre implementation, on prend un message au hasard parmis
     * ceux proposes dans le fichier.
     */
    private void forgeMails() {
        Reader reader = new Reader("prankMessages.txt");
        String[] messages = reader.bufferedReading().split("==");

        Random randomGen = new Random();
        int message;

        // On va cree une liste de mails par groupe
        for (int group = 0; group < numberOfGroups; ++group) {
            // On recupere un message au bol
            message = randomGen.nextInt();
            // Le sujet de messages est sur la premiere ligne du message
            String subject = "to extract";

            for(String victimMail : victimsMails) {

                // TODO checker quelle valeur il faut vraiment mettre dans toSMTP et toInMail
                if(!victimMail.equals(System.getProperty("line.separator"))) {
                    Mail mail = new Mail();
                    mail.setFrom(fromSMTP);
                    mail.setTo(toSMTP);
                    mail.setFromInMail(fromInMail);
                    mail.setToInMail(victimMail);
                    mail.setCc(witnesses);
                    mail.setSubject(subject);
                    mail.setMessage(messages[message]);

                    forgedMails.add(mail);
                }
            }
        }
    }
}