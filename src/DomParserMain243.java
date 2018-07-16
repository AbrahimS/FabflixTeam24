/***
  import java.io.IOException;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class DomParserMain243 {

    private List<Movie> myMovies;
    private Map<String, Integer> movieHashMap;
    
    private List<Genres> myGenres;
    private Map<String, Integer> genreHashMap;
   
    private List<GenreInMovie> genreInMovie;
    private Map<Integer, String> genreInMovieHashMap;
    private int numOfGenres;
    
    private List<Star> myStars;
    private Map<String, Integer> starHashMap;
    private int numOfStars;

    private Document domMain;
    private Document domAct;
    private Document domCast;
    
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;

    private List<StarInMovie> starInMovie;
    private Map<String, String> starInMovieHashMap;

    public DomParserMain243() {
        //create a list to hold the employee objects
        myMovies = new ArrayList<>();
        movieHashMap = new HashMap<String, Integer>();
        
        myGenres = new ArrayList<>();
        genreHashMap = new HashMap<String, Integer>();
        genreHashMap.put("action", 1);
        genreHashMap.put("adult", 2);
        genreHashMap.put("adventure",3);
        genreHashMap.put("animation", 4);
        genreHashMap.put("aiography", 5);
        genreHashMap.put("comedy", 6);
        genreHashMap.put("crime", 7);
        genreHashMap.put("documentary", 8);
        genreHashMap.put("drama", 9);
        genreHashMap.put("family", 10);
        genreHashMap.put("fantasy", 11);
        genreHashMap.put("history", 12);
        genreHashMap.put("horror", 13);
        genreHashMap.put("music", 14);
        genreHashMap.put("musical", 15);
        genreHashMap.put("mystery", 16);
        genreHashMap.put("reality-TV", 17);
        genreHashMap.put("romance", 18);
        genreHashMap.put("sci-fi", 19);
        genreHashMap.put("sport", 20);
        genreHashMap.put("thriller", 21);
        genreHashMap.put("war", 22);
        genreHashMap.put("western", 23);
        
        myGenres = new ArrayList<>();
        genreInMovie = new ArrayList<>();
        genreInMovieHashMap = new HashMap<Integer, String>();
        
        numOfGenres = 100;

        starHashMap = new HashMap<String, Integer>();
        myStars = new ArrayList<>();
        numOfStars= 1;

        starInMovie = new ArrayList<>();
        starInMovieHashMap = new HashMap<String, String>();


    }

    public void runExample() {

        //parse the xml files and get the dom objects
        parseXmlFile();

        //get each element and create a movie, genre and stars elements
        parseDocument();

        //get each element and creat a star
        parseDocumentActor();

        //connect star to movie
        parseDocumentCast();

        //Iterate through the list and print the data
        printData();

        //write to data base
        writeDataToDataBase();

    }

    private void writeDataToDataBase()
    {
         DatabaseUploader database = new  DatabaseUploader(myMovies, myGenres, genreInMovie, myStars, starInMovie);
    }

    private void parseXmlFile() {
        //get the factory
        dbf = DocumentBuilderFactory.newInstance();


        try {

            //Using factory get an instance of document builder
            db = dbf.newDocumentBuilder();

           // DocumentBuilder db3 = dbf3.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            domMain = db.parse("mains243.xml");
            
            domAct = db.parse("actors63.xml");

            domCast = db.parse("casts124.xml");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void parseDocumentCast()
    {
        Element docCstEle = domCast.getDocumentElement();
        NodeList nlCstEle =  docCstEle.getElementsByTagName("m"); 
        if (nlCstEle != null && nlCstEle.getLength() > 0) {
            for (int i = 0; i < nlCstEle.getLength(); i++) {

                Element cstEl = (Element) nlCstEle.item(i);
                String movieId;
                String stagename;
                try {
                    movieId = getTextValue(cstEl, "f");
                    stagename = getTextValue(cstEl, "a");
                    if(starHashMap.containsKey(stagename))
                    {
                        String starId = String.valueOf(starHashMap.get(stagename));
                        StarInMovie sm = new StarInMovie(starId, movieId);
                        starInMovie.add(sm);
                    }
                    else
                    {
                        continue;
                    }

                }
                 catch(NumberFormatException ex){ 
                    //System.out.println("Inconsistant data Year=" + ex.getMessage().replace("For input string: ",""));
                    System.out.println(ex);
                    continue;
                }
                catch(NullPointerException ex){
                    //System.out.println("Inconsistant data Year = null");
                    System.out.println("stagename= " + ex);
                    continue;
                }

            }
        }
    }
    
    private void parseDocumentActor(){
        Element docActEle = domAct.getDocumentElement();
        NodeList nlActs = docActEle.getElementsByTagName("actor"); 
        if (nlActs != null && nlActs.getLength() > 0) {
            for (int i = 0; i < nlActs.getLength(); i++) {
              //get the director Element and all films
                Element actEl = (Element) nlActs.item(i);
                String id;
                String name;
                int birthYear;
                try {

                        name = getTextValue(actEl, "stagename");
                        birthYear = getIntValue(actEl, "dob");
                        

                        } catch(NumberFormatException ex){ 
                           //System.out.println("Inconsistant data Year=" + ex.getMessage().replace("For input string: ",""));
                        System.out.println(ex);
                           continue;
                        }
                        catch(NullPointerException ex){
                           System.out.println("Inconsistant data stagename = null");
                           continue;
                        }
               
                if(!starHashMap.containsKey(name))
                { //add it to list
                    Star s = new Star(String.valueOf(numOfStars), name, birthYear);
                    myStars.add(s);
                    starHashMap.put(name, numOfStars);
                    numOfStars++;
            
                }

            }
        }

    }

    private void parseDocument() {
        //get the root elememt for main243.xml
        Element docMainEle = domMain.getDocumentElement();

        //get the root elememt for cast124.xml
        //Element docCastEle = domCast.getDocumentElement();

        //get a nodelist of director elements 
        NodeList nldirfilms = docMainEle.getElementsByTagName("directorfilms"); 
        if (nldirfilms != null && nldirfilms.getLength() > 0) {
            for (int i = 0; i < nldirfilms.getLength(); i++) {

                //get the director Element and all films
                Element dirEl = (Element) nldirfilms.item(i);

                // get a nodelist of films for director
                NodeList nlfilms = dirEl.getElementsByTagName("film"); 
                if (nlfilms != null && nlfilms.getLength() > 0) {
                    for (int j = 0; j < nlfilms.getLength(); j++) {
                    
                        //get the film Element
                        Element filmEl = (Element) nlfilms.item(j);
                      Movie m;
                      try {

                        //get the movie object
                        m = getMovie(dirEl, filmEl);
                        

                        } catch(NumberFormatException ex){ 
                           System.out.println("Inconsistant data Year=" + ex.getMessage().replace("For input string: ",""));
                           continue;
                        }
                        catch(NullPointerException ex){
                           System.out.println("Inconsistant data Year = null");
                           continue;
                        }

                        if(!movieHashMap.containsKey(m.getId()))
                            { //add it to list
                                myMovies.add(m);
                                movieHashMap.put(m.getId(), 1);
                                updateGenres(m.getId(), filmEl);
                            }
                       
                    }
                }
            }
        }
    }

        private void updateGenres(String movieId, Element filmEl)
        {

             NodeList nlCats = filmEl.getElementsByTagName("cats"); //stop
                if (nlCats != null && nlCats.getLength() > 0) {
                    for (int j = 0; j < nlCats.getLength(); j++) {
                    
                        //get the cat Element
                        Element catEl = (Element) nlCats.item(j);
                        int id;
                        String name;
                        try{
                            getTextValue(catEl, "cat");
                        }
                        catch(NullPointerException ex){
                           System.out.println("Inconsistant data cat = null");
                           continue;
                        }
                        if(genreHashMap.get(getTextValue(catEl, "cat")) == null)
                        {
                            name = getTextValue(catEl, "cat");
                            id = numOfGenres;
                            numOfGenres++;
                            
                            
                            //Create a new genre with the value read from the xml nodes
                            Genres g = new Genres(id, name);
                            myGenres.add(g);
                            genreHashMap.put(name, id);
                        }
                        else
                        {
                            name = getTextValue(catEl, "cat");
                            id = genreHashMap.get(name);

                        }

                         // update movie in genre
                        GenreInMovie gim = new  GenreInMovie(id, movieId);
                       
                        if(genreInMovieHashMap.get(id) == null || !genreInMovieHashMap.get(id).equals(movieId))
                        {
                            genreInMovieHashMap.put(id,movieId);
                            genreInMovie.add(gim);
                        }
                        

                       
                    }
                }
        }

        private Movie getMovie(Element dirEl, Element filmEl) {

        //for each <employee> element get text or int values of 
        //name ,id, age and name
        String id = getTextValue(filmEl, "fid");
        String title = getTextValue(filmEl, "t");
        int year = getIntValue(filmEl, "year");
        String director = getTextValue(dirEl, "dirname");

        //Create a new Employee with the value read from the xml nodes
        Movie m = new Movie(id, title, year, director);

        return m;
    }
    

    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }


    private int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }
**/
    /**
     * Iterate through the list and print the
     * content to console
     */
/**
    private void printData() {
         try
        {
            PrintWriter writer = new PrintWriter("parsedData.txt");    
       
            
            Iterator<Movie> it = myMovies.iterator();
            String line;
            while (it.hasNext()) {
                line = it.next().toString();
                System.out.println(line);
                writer.println(line);
            }
            

            Iterator<Genres> it2 = myGenres.iterator();
            while (it2.hasNext()) {
                line = it2.next().toString();
                System.out.println(line);
                writer.println(line);
            }
           
            
            Iterator<GenreInMovie> it3 = genreInMovie.iterator();
            while (it3.hasNext()) {
                line = it3.next().toString();
                System.out.println(line);
                writer.println(line);
            }
            

            Iterator<Star> it4 = myStars.iterator();
            while (it4.hasNext()) {
                line = it4.next().toString();
                System.out.println(line);
                writer.println(line);
            }
            

             Iterator<StarInMovie> it5 = starInMovie.iterator();
            while (it5.hasNext()) {
                line = it5.next().toString();
                System.out.println(line);
                writer.println(line);
            }
            System.out.println("Num of Movies '" + myMovies.size() + "'.");
            System.out.println("Num of genres '" + myGenres.size() + "'.");
            System.out.println("Num of genreInMovies '" + genreInMovie.size() + "'.");
            System.out.println("Num of stars '" + myStars.size() + "'.");
            System.out.println("Num of starsInMovies'" + starInMovie.size() + "'.");
            



            writer.close();
        }
        catch (FileNotFoundException ex)  
        {
            System.out.println("could not open file");
        }
    }

    public static void main(String[] args) {
        //create an instance
        DomParserMain243 dpe = new DomParserMain243();

        //call run example
        dpe.runExample();
    }

}**/