package com.company;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.io.*;
import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;

import static com.company.Methode.chainSouter;
import static com.company.Methode.dictionairyWords;

public class Methode {
    public Methode(){
    }
    /**
     * chainSouter permet de retouner l'ensemble de répartition d'une chaine de charactère
     * @param chain
     * @return
     */
    public static LinkedList<Character> chainSouter(String chain){
        LinkedList<Character> chaine = new LinkedList<>();
        char[] charactereArray = chain.toCharArray();
        for (int i = 0; i < charactereArray.length; i++){
            chaine.add(charactereArray[i]);
        }
        chaine.sort(Character::compareTo);
        return chaine;
    }

    /**
     * listWord permet de retourner la liste des mots ce trouver dans le fichier dico.txt ou minidico.txt
     * @return
     */
    public static LinkedList<String> listWord(){

        LinkedList<String> dictionairyWords = new LinkedList<>();
        File file = new File("C:\\Users\\bayed\\IdeaProjects\\TP_P2-somme\\src\\com\\company\\dico.txt");
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line ;
            while ((line = bufferedReader.readLine()) != null) {
                dictionairyWords.add(line.strip());

            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return dictionairyWords;
    }

    /**
     * addValue permet d'ajouter un élément à une liste chainée correspondant à la valeur du clé du dictionnaire
     * @param lista
     * @param valueToAdd
     * @param dico
     */
    public static void addValue(LinkedList<Character> lista, String valueToAdd,HashMap < LinkedList<Character>, LinkedList<String> > dico ){
        LinkedList<String> list1 =  dico.get(lista);
        dico.remove(lista);
        list1.add(valueToAdd);
        dico.put(lista, list1);
    }

    /**
     * dictionairyWords permet de retourner un dictionnaire donc les clé sont des ensemble de répartition et les valeurs sont des listes
     * chainées de chaine de charactère.
     * @param dictionairyWord
     * @return
     */
    public static HashMap < LinkedList<Character>, LinkedList<String> >  dictionairyWords(LinkedList<String> dictionairyWord){
        HashMap < LinkedList<Character>, LinkedList<String> > dico1 = new HashMap<>();
        for (String list1: dictionairyWord
             ) {
            LinkedList<Character> att = chainSouter(list1);
            if(!(dico1.containsKey(att))){
                LinkedList<String> list3 = new LinkedList<>();
                list3.add(list1);
                dico1.put(att,list3);
            }
            else if (dico1.containsKey(att))
            {
                addValue(att, list1, dico1);
            }
        }

        return dico1;
    }

    /**
     * substraction est la méthode qui permet la soustraction entre deux ensemble et qui retourne "le rest"
     * @param request
     * @param wholeWord1
     * @return
     */
    public static LinkedList<Character> substraction(LinkedList<Character> request, LinkedList<Character> wholeWord1){
        LinkedList<Character> wholeWord2 = new LinkedList<>();
        if (request.size() > wholeWord1.size() ) {
            while ( !wholeWord1.isEmpty())  {
                try {
                    if (wholeWord1.getFirst().compareTo(request.getFirst()) == 0) {
                        request.removeFirst();
                        wholeWord1.removeFirst();
                        continue;
                    }
                }catch (NoSuchElementException e){
                    return null;
                }
                if (wholeWord1.getFirst().compareTo(request.getFirst()) > 0) {
                    wholeWord2.add(request.poll());
                    continue;
                }
                if(wholeWord1.getFirst().compareTo(request.getFirst()) < 0 ){
                    wholeWord2 = null;
                    return wholeWord2;
                }
            }
            wholeWord2.addAll(request);
        }
        return wholeWord2;
    }

    /**
     * p2_somme est la méthode qui permet de retourner l'ensemble des paires de mots ou du moins d'ensemble de répartition
     * permet de former un l'ensemble de la chaine de caractère mis en paramètre (la requête)
     * @param request
     * @param dictionairy
     * @return
     */
    public static HashSet< HashSet<LinkedList<Character>> > p2_somme(String request,HashMap < LinkedList<Character>, LinkedList<String> > dictionairy ){
        HashSet< HashSet<LinkedList<Character>> > set = new HashSet<>();
        for (LinkedList<Character> linkedList : dictionairy.keySet()) {
            LinkedList<Character> setRequest = chainSouter(request);
            LinkedList<Character> subtractSet = new LinkedList<>();
            subtractSet.addAll(linkedList);
            LinkedList<Character> verificationSet = substraction(setRequest,linkedList);
            if(verificationSet != null) {
                if(dictionairy.containsKey(verificationSet)){
                    HashSet< LinkedList<Character> > set1 = new HashSet<>();
                    set1.add(subtractSet);
                    set1.add(verificationSet);
                    set.add(set1);
                }
            }
        }
        return set;
    }
}
