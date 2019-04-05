package com.company;
import java.io.*;
import java.util.*;

class Activity implements Comparable<Activity>
{
	int ID;
	int Start;
	int Finish;
	int Value;

	Activity(String id, String start, String finish, String value)
	{
		ID = Integer.valueOf(id);
		Start = Integer.valueOf(start);
		Finish = Integer.valueOf(finish);
		Value = Integer.valueOf(value);
	}

	@Override
	public int compareTo(Activity activity)
	{
		int compareFinish = activity.getFinish();

		/* For Ascending order*/
		return this.Finish - compareFinish;
	}

	public int getFinish()
	{
		return this.Finish;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append(this.ID).append(" ").append(this.Start).append(" ").append(this.Finish).append(" ").append(this.Value);
		return builder.toString();
	}
}

public class Homework7
{

	public static void log(String str)
	{
		System.out.println(str);
	}

	public static void isUnique(boolean unique)
	{
		if(unique)
		{
			log("IT HAS A UNIQUE SOLUTION");
		}
		else
		{
			log("IT HAS MULTIPLE SOLUTIONS");
		}
	}

	public static String printUnique(boolean unique)
	{
		if(unique)
		{
			return "IT HAS A UNIQUE SOLUTION";
		}
		else
		{
			return "IT HAS MULTIPLE SOLUTIONS";
		}
	}



	public static String buildStringOutput(int maxValue, ArrayList<Integer> finalActivityArray, String uniqueness)
	{
		String max = String.valueOf(maxValue);
		String activityString;

		StringBuilder builder = new StringBuilder();

		Collections.sort(finalActivityArray);

		for (int i = 0; i < finalActivityArray.size(); i++)
		{
			builder.append(finalActivityArray.get(i)).append(" ");
		}

		activityString = builder.toString();

		return max + "\n" + activityString + "\n" + uniqueness;
	}

	public static void main(String[] args)
	{
		String inputFile = args[0];
		String outputFile = args[1];

		File file = new File("/Users/davidskinner/Documents/Repositories/GreedyAlgorithm/" + inputFile);

		int numberOfActivities = 0;
		int interval;
		ArrayList<Activity> activities = new ArrayList<>();

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			int counter = 0;
			while ((st = br.readLine()) != null)
			{
				String[] splitter = st.split(" ");

				if (counter == 0)
				{
					// read in first line
					numberOfActivities = Integer.valueOf(splitter[0]);
					log("The number of activites is: " + String.valueOf(numberOfActivities));

					interval = Integer.valueOf(splitter[1]);
					log("The interval is : 0 to " + String.valueOf(interval));

				} else
				{
					//import the activities into a list
					activities.add(new Activity(splitter[0], splitter[1], splitter[2], splitter[3]));
				}
				System.out.println(st);

				counter++;
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		}

		log("");

		//Sort the activities by monotonically increasing finish time
		Collections.sort(activities);

		for (Activity a:
		     activities)
		{
			log(a.toString());
		}

		//variable to see if solution is unique or not
		boolean unique = false;

		// maximum value
		int maxValue = -1;

		// Use dynamic programming
		// stored values
		int cache[] = new int[numberOfActivities];

		for (int i = 0; i < numberOfActivities ; i++)
		{
			cache[i] = activities.get(i).Value;
		}

		for(int i=1; i < numberOfActivities; i++)
		{
			for(int j=0; j < i; j++)
			{
				if(activities.get(j).Finish <= activities.get(i).Start)
					cache[i]= Math.max(cache[j]+activities.get(i).Value, cache[i]);
				maxValue= Math.max(cache[i], maxValue);
			}
		}

		log(String.valueOf("max value: " + maxValue));
		int bigmax = maxValue;

		ArrayList<Integer> activityPositions = new ArrayList<>();

		// count up through the activities
		for(int i = numberOfActivities - 1; i >= 0 ; i--)
		{
			if(cache[i] == maxValue)
			{
				maxValue -= activities.get(i).Value;
				 activityPositions.add(activities.get(i).ID);
			}
		}

		//decide if there are duplicates
		
		// check each activity in activity positions

		for (int a : activityPositions)
		{

		}
		
		int maxCounter = 0;
		for (int i = 0; i < cache.length; i++)
		{
			if(cache[i] == bigmax)
			{
				maxCounter++;
			}
		}

		if(maxCounter > 1)
		{
			unique = false;
		}
		else
		{
			unique = true;
		}

		try (PrintStream out = new PrintStream(new FileOutputStream(outputFile))) {
			out.print(buildStringOutput(bigmax, activityPositions , printUnique(unique)));

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}


	}
}
