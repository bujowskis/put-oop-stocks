package filereader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class dataparser {
	static HashMap<String, List<ohlcv>> data = new HashMap<String, List<ohlcv>>();
	public static void main(String[] args)
		throws IOException
	{
		
		List<String> listOfStrings = new ArrayList<String>();
		BufferedReader bf = new BufferedReader(new FileReader("/home/white07/Desktop/Java/main/filereader/TM.csv"));
		String line = bf.readLine();
		while (line != null) {
			listOfStrings.add(line);
			line = bf.readLine();
		}
		bf.close();
		String[] array= listOfStrings.toArray(new String[0]);
		for(int i=0; i<array.length;i++) 
		{
			String[] values = array[i].split(",");
           	ohlcv row_item = new ohlcv(values[1],values[2],values[3],values[4],values[5]);
			values[0] = values[0].split(" ")[1];
            data.put(values[0],row_item);
		}
		System.out.println(data.size());
		System.out.println(((ohlcv) data.get("09:35")).getLow());

	}
}
