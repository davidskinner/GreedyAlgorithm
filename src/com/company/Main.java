package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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


public class Main
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

	public static void main(String[] args)
	{

		File file = new File("/Users/davidskinner/Documents/Repositories/GreedyAlgorithm/input2.txt");

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

//		for (Activity a : activities)
//		{
//			log(a.toString());
//		}

		//variable to see if solution is unique or not
		boolean unique = false;

		// maximum value
		int maxValue = -1;

		// Use dynamic programming
		// stored values
		int dynamicProgramming[] = new int[numberOfActivities];

		for (int i = 0; i < numberOfActivities ; i++)
		{
			dynamicProgramming[i] = activities.get(i).Value;
		}

		ArrayList<Integer> uniqueNums = new ArrayList<>();


		for(int i=1; i < numberOfActivities; i++)
		{
			for(int j=0; j < i; j++)
			{
				if(activities.get(j).Finish <= activities.get(i).Start)
					dynamicProgramming[i]= Math.max(dynamicProgramming[j]+activities.get(i).Value, dynamicProgramming[i]);
				maxValue= Math.max(dynamicProgramming[i], maxValue);
			}
		}

		log(String.valueOf("max value: " + maxValue));

		int activityPositions[] = new int[numberOfActivities];
		int k = 0;

		for(int i = numberOfActivities - 1; i >= 0 ; i--)
		{
			if(dynamicProgramming[i] == maxValue)
			{
				maxValue -= activities.get(i).Value;
				activityPositions[k] = activities.get(i).ID;
				k++;
			}
		}

		for (int i = k-1 ; i >= 0 ; i--)
		{
			log(String.valueOf(activityPositions[i]));
		}

		// "IT HAS A UNIQUE SOLUTION" or "IT HAS MULTIPLE SOLUTIONS"
		isUnique(unique);
	}
}
