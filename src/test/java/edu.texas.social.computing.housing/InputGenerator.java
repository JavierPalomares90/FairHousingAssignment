package edu.texas.social.computing.housing;

import java.io.*;
import java.util.Date;
import java.util.Random;

public class InputGenerator
{
    public static void main(String[] args) throws IOException
    {
        if (args.length < 1) return;
        int numAgents = Integer.parseInt(args[0]);
        String filepath = "src/test/resources/Test_Num_Agents_" + numAgents + ".txt";

        BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
        // Write the number of agents to the file
        bw.write(args[0]);
        bw.write("\n");

        Random random = new Random();
        // For each agent, get a random list of preferences
        for (int i = 0; i < numAgents; i++)
        {
            int[] randomPrefs = getRandomPreferences(random, numAgents);
            for (int j : randomPrefs)
            {
                bw.write(j + " ");
            }
            bw.write("\n");
        }
        bw.close();
    }

    private static int[] getRandomPreferences(Random r, int n)
    {
        int[] array = new int[n];
        for (int i=0; i < n; i++)
        {
            array[i] = i+1;
        }
        for (int i=0; i<array.length; i++)
        {
            int randomPosition = r.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }

}
