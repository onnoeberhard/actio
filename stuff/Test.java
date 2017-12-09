package test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Test{

    public static void main(String[] args) {
    	String s = "name{{:}}NaturErlebnisBad Luthe{{;}}description{{:}}bad halt{{;}}location{{:}}1{{;}}address{{:}}a{{,}}An der Boehmerke 9\n31515 Wunstorf\nGermany{{:}}c{{,}}52.423823{{,}}9.471795{{;}}activities{{:}}2{{;}}category{{:}}2.1{{;}}rating{{:}}4.4";
    	System.out.println(explode(s).toString());
    }
    
    public static JSONObject explode(String string) throws JSONException {
    	JSONObject json = new JSONObject();
    	String[] level1 = string.split("[{][{][;][}][}]");
    	for(int i = 0; i < level1.length; i++) {
    		if(level1[i].contains("{{:}}")) {
    			String[] level2 = level1[i].split("[{][{][:][}][}]");
    			boolean l2_obj = false;
    			for(int ii = 0; ii < level2.length; ii++) {
    				if(level2[ii].contains("{{,}}")) {
    					String[] level3 = level2[ii].split("[{][{][,][}][}]");
    					if(!l2_obj)
    						json.put(level2[0], new JSONObject());
						l2_obj = true;
    					json.getJSONObject(level2[0]).put(level3[0], new JSONArray());
    	    			for(int iii = 0; iii < level3.length; iii++) {
//    	    				if(level2[ii].contains("{{,}}")) {
//    	    					String[] level3 = level2[ii].split("[{][{][,][}][}]");
//    	    	    			json.put(level3[0], new JSONArray());
//    	    				} else {
    	    					json.getJSONObject(level2[0]).getJSONArray(level3[0]).put(level3[iii]);
//    	    				}
    	    			}
    				} else {
    	    			json.put(level2[0], new JSONArray());
    					json.getJSONArray(level2[0]).put(level2[ii]);
    				}
    			}
    		} else
    			json.put(Integer.toString(i), level1[i]);
    	}
    	return json;
    }
}
