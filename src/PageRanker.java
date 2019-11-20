//Name(s): Phattharapong Poolthong, Anchisa Suklom, Narinda Adam
//ID: 5888136, 6088015, 6088064
//Section: 3
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class implements PageRank algorithm on simple graph structure.
 * Put your name(s), ID(s), and section here.
 *
 */
public class PageRanker {
	
	/**
	 * This class reads the direct graph stored in the file "inputLinkFilename" into memory.
	 * Each line in the input file should have the following format:
	 * <pid_1> <pid_2> <pid_3> .. <pid_n>
	 * 
	 * Where pid_1, pid_2, ..., pid_n are the page IDs of the page having links to page pid_1. 
	 * You can assume that a page ID is an integer.
	 */
	
	//Declare variables
	List<String> rawData; //read each line
	Map<Integer, List<Integer>> linkToP;
	Map<Integer, Integer> outFromQ;
	Set<Integer> sinkNode;
	Set<Integer> outLink;
	Set<Integer> pages;
	
	public void loadData(String inputLinkFilename){
		rawData = new ArrayList<String>();
		File file = new File(inputLinkFilename);
		try {
			BufferedReader br = new BufferedReader (new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				rawData.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will be called after the graph is loaded into the memory.
	 * This method initialize the parameters for the PageRank algorithm including
	 * setting an initial weight to each page.
	 */

	public void initialize(){
		linkToP = new TreeMap<Integer, List<Integer>>();
		outFromQ = new TreeMap<Integer, Integer>();
		outLink = new TreeSet<Integer>();
		pages = new TreeSet<Integer>();
		sinkNode = new TreeSet<Integer>();

		for(String line : rawData) {
			String[] elements = line.split(" ");
			List<Integer> temp = new ArrayList<Integer>();
			for(int i = 0; i < elements.length; i++ ) {
				pages.add(Integer.parseInt(elements[i]));
				sinkNode.add(Integer.parseInt(elements[i]));
			}
			for(int i = 1; i < elements.length; i++) {
				outLink.add(Integer.parseInt(elements[i]));
				temp.add(Integer.parseInt(elements[i]));
				
			}
			linkToP.put(Integer.parseInt(elements[0]), temp);
		}

		for(Integer key : pages) {
			if(!linkToP.containsKey(key)) {
				linkToP.put(key, new ArrayList<Integer>());
			}
		}
		
		for(Integer key: linkToP.keySet()) {
			if(!linkToP.get(key).isEmpty()) {
				for(Integer page: linkToP.get(key)) {
					if(!outFromQ.containsKey(page)) {
						outFromQ.put(page, 1);
					}
					else {
						outFromQ.put(page, outFromQ.get(page)+1);
					}
				}
			}
		}
		int count = 0;
		for(Integer k: outFromQ.keySet()) {
			System.out.println("key: "+ k+ "# "+ outFromQ.get(k));
			count++;
			if(count > 10) {
				break;
			}
		}
		sinkNode.removeAll(outLink);
	}
	
	/**
	 * Computes the perplexity of the current state of the graph. The definition
	 * of perplexity is given in the project specs.
	 */
	public double getPerplexity(){return 0;}
	
	/**
	 * Returns true if the perplexity converges (hence, terminate the PageRank algorithm).
	 * Returns false otherwise (and PageRank algorithm continue to update the page scores). 
	 */
	public boolean isConverge(){return false;}
	
	/**
	 * The main method of PageRank algorithm. 
	 * Can assume that initialize() has been called before this method is invoked.
	 * While the algorithm is being run, this method should keep track of the perplexity
	 * after each iteration. 
	 * 
	 * Once the algorithm terminates, the method generates two output files.
	 * [1]	"perplexityOutFilename" lists the perplexity after each iteration on each line. 
	 * 		The output should look something like:
	 *  	
	 *  	183811
	 *  	79669.9
	 *  	86267.7
	 *  	72260.4
	 *  	75132.4
	 *  
	 *  Where, for example,the 183811 is the perplexity after the first iteration.
	 *
	 * [2] "prOutFilename" prints out the score for each page after the algorithm terminate.
	 * 		The output should look something like:
	 * 		
	 * 		1	0.1235
	 * 		2	0.3542
	 * 		3 	0.236
	 * 		
	 * Where, for example, 0.1235 is the PageRank score of page 1.
	 * 
	 */
	public void runPageRank(String perplexityOutFilename, String prOutFilename){}
	
	
	/**
	 * Return the top K page IDs, whose scores are highest.
	 */
	public Integer[] getRankedPages(int K){return null;}
	
	public static void main(String args[])
	{
	long startTime = System.currentTimeMillis();
		PageRanker pageRanker =  new PageRanker();
		pageRanker.loadData("citeseer.dat");
		pageRanker.initialize();
		pageRanker.runPageRank("perplexity.out", "pr_scores.out");
		Integer[] rankedPages = pageRanker.getRankedPages(100);
	double estimatedTime = (double)(System.currentTimeMillis() - startTime)/1000.0;
		
		System.out.println("Top 100 Pages are:\n"+Arrays.toString(rankedPages));
		System.out.println("Proccessing time: "+estimatedTime+" seconds");
	}
}