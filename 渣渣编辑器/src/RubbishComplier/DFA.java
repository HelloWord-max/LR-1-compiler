package RubbishComplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
public class DFA  implements Cloneable{
//	private Map<Character, String> DFA = new HashMap<Character, String>();
//	private Map<Character, DFA> NEXT_DFA = new HashMap<Character, DFA>();
//	private int state=-1;
//	public Map<Character, String> getDFA() {
//		return DFA; 
//	}
//	public void setDFA(Map<Character, String> dFA) {
//		DFA = dFA;
//	}
//	public int getState() {
//		return state;
//	}
//	public void setState(int state) {
//		this.state = state;
//	}
//	public Map<Character, DFA> getNEXT_DFA() {
//		return NEXT_DFA;
//	}
//	public void setNEXT_DFA(Character character, DFA dfa) {
//		NEXT_DFA.put(character, dfa);
//	}
	
	private Map<String, List<List<List<String>>>> DFA = new HashMap<String, List<List<List<String>>>>();
	private Map<String, DFA> NEXT_DFA = new HashMap<String, DFA>();
	private int state=-1;
	private int r=-1;
	private Map<String, Integer> next_r=new HashMap<String, Integer>();
	public Map<String, List<List<List<String>>>> getDFA() {
		return DFA; 
	}
	public void setDFA(Map<String, List<List<List<String>>>> dFA) {
		DFA = dFA;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Map<String, DFA> getNEXT_DFA() {
		return NEXT_DFA;
	}
	public void setNEXT_DFA(String Str, DFA dfa) {
		NEXT_DFA.put(Str, dfa);
	}
	protected DFA clone() throws CloneNotSupportedException {
        // 实现clone方法
        return (DFA) super.clone();
    }
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public Map<String, Integer> getNext_r() {
		return next_r;
	}
	public void setNext_r(String follow) {
		this.next_r.put(follow, -1);
	}
}
