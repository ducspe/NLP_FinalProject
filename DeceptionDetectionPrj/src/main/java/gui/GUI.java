package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;

import de.unihamburg.informatik.nlp.deceptiondetection.pipeline.PipelineMain;

public class GUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GUI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/home.jsp");
		view.forward(request, response);
	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		ClassLoader loader = GUI.class.getClassLoader();
		if (action == null) {
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		} else if (action.equals("train")) {
		
			
	    	
			System.out.println("POST method action = " + action);
	
			
			String[] trainingArguments = new String[]{"-t", "0"};
	        long start = System.currentTimeMillis();
	
	        String modelPath = getServletContext().getRealPath("/") + "WEB-INF/classes/model/";  
	        new File(modelPath).mkdirs();
	        File modelDir = new File(modelPath);
	        
	        String resltPath = getServletContext().getRealPath("/") + "WEB-INF/classes/results/";  
	        new File(resltPath).mkdirs();
	        File resultDir = new File(resltPath+"userinputeval.csv");  
	        
	        File newsTrain = new File(getServletContext().getRealPath("/") + "WEB-INF/classes/dataset/train.csv");
	        File newsTest = new File(getServletContext().getRealPath("/") + "WEB-INF/classes/dataset/test.csv");
	        
	        File stopWordsFile = new File(loader.getResource("stopwords.txt").getFile());
	        try {
		        //PipelineMain.writeModel(newsTrain, modelDir, stopWordsFile);
		        //PipelineMain.trainModel(modelDir, trainingArguments);
		        PipelineMain.classifyTestFile(modelDir, newsTest, resultDir, stopWordsFile);
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	        long now = System.currentTimeMillis();
	        UIMAFramework.getLogger().log(Level.INFO, "Time: " + (now - start) + "ms");
	        // Read evaluation file
	        BufferedReader br = new BufferedReader(new FileReader(new File(getServletContext().getRealPath("/") + "WEB-INF/classes/results/eval.txt")));
	        List <String> message = new ArrayList<String>();
	        try {
	            StringBuilder sb = new StringBuilder();
	            String line = br.readLine();
	
	            while (line != null) {
	                sb.append(line);
	                sb.append(System.lineSeparator());
	                line = br.readLine();
	            }
	            message = Arrays.asList(sb.toString().split(" "));
	        } finally {
	            br.close();
	        }
	        request.setAttribute("message", message);
	        request.getRequestDispatcher("/trainsuccess.jsp").forward(request, response);
	 
		}else if (action.equals("detect")) {
			String userInput = request.getParameter("textarea1");
			System.out.println("User wrote: " + userInput);      
	        
	        String userInputFile = loader.getResource("dataset/userdummytest.csv").getFile();
	        
			FileOutputStream fw = new FileOutputStream(userInputFile);
			String content = userInput + "\tlie\n";
			fw.write(content.getBytes());

			fw.getFD().sync();
			fw.close();
			
			request.getRequestDispatcher("/testresult.jsp").forward(request, response);
			
		} else if(action.equals("showresult")) {
			
			String modelPath = getServletContext().getRealPath("/") + "WEB-INF/classes/model/";  
	        new File(modelPath).mkdirs();
	        File modelDir = new File(modelPath);
	        
	        String resltPath = getServletContext().getRealPath("/") + "WEB-INF/classes/results/";  
	        new File(resltPath).mkdirs();
	        File resultDir = new File(resltPath+"userinputeval.csv");
			File flushedUserInputFile = new File(loader.getResource("dataset/userdummytest.csv").getFile());
			
			System.out.println("Done");
			
	        File stopWordsFile = new File(getServletContext().getRealPath("/") + "WEB-INF/classes/stopwords.txt");
			try {
				PipelineMain.classifyUserInput(modelDir, flushedUserInputFile, resultDir, stopWordsFile);
			} catch (ResourceInitializationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UIMAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Read evaluation file
	        BufferedReader br = new BufferedReader(new FileReader(getServletContext().getRealPath("/") + "WEB-INF/classes/results/userinputeval.csv"));
			String message = "Evaluation message";
	        try {
	            StringBuilder sb = new StringBuilder();
	            String line = br.readLine();
	
	            while (line != null) {
	                sb.append(line);
	                sb.append(System.lineSeparator());
	                line = br.readLine();
	            }
	            String[] wholeMessage = sb.toString().split("\\s+");
	            message = wholeMessage[wholeMessage.length-1];
	        } finally {
	            br.close();
	        }
	        request.setAttribute("message", message);
			request.getRequestDispatcher("/showresult.jsp").forward(request, response);
		}
	}
}
