package skrib.util;
import java.io.*;

public class TrieNode{

    private TrieNode[] children = new TrieNode[26];
    private boolean occupied = false;

	public TrieNode(){ }

    public TrieNode( boolean occupied ){
        this.occupied = occupied;
    }

	public void makeOccupied(){
		this.occupied = true;
	}

	public boolean isOccupied(){
		return this.occupied;
	}

    public void insert(char[] input){
		this.load(input, 0);
	}

    public void insert(CharSequence input){
		this.load(input, 0);
	}

	public void load(char[] input, int offsetStart){
		char c = input[offsetStart];
		int offset = (int)(Character.toLowerCase( c )) - 97;
		if( offset < 0 || offset > 25 ){
			return;
		}
		boolean finalChar = (offsetStart == input.length-1);
		TrieNode next = this.children[ offset ];
		if( next == null ){
			next = new TrieNode( finalChar );
			this.children[ offset ] = next;
		}
		if( finalChar ){
			return;
		}else{
			next.load( input, offsetStart +1);
		}
	}

	public void load(CharSequence input, int offsetStart){
		char c = input.charAt(offsetStart);
		int offset = (int)(Character.toLowerCase( c )) - 97;
		if( offset < 0 || offset > 25 ){
			return;
		}
		boolean finalChar = (offsetStart == input.length()-1);
		TrieNode next = this.children[ offset ];
		if( next == null ){
			next = new TrieNode( finalChar );
			this.children[ offset ] = next;
		}
		if( finalChar ){
			return;
		}else{
			next.load( input, offsetStart +1);
		}
	}

	public boolean lookUp(CharSequence input){
		TrieNode current = this;
		for( int i=0;i<input.length();i++ ){
			char c = input.charAt(i);
			int offset = (int)(Character.toLowerCase( c )) - 97;
			TrieNode next = current.children[ offset ];
			if( next == null ) return false;
			else{
				current = next;
			}
		}
		if( current == null ) return false;
		else return current.isOccupied();
	}


	public boolean lookUp(char[] input){
		TrieNode current = this;
		for( char c : input ){
			int offset = (int)(Character.toLowerCase( c )) - 97;
			TrieNode next = current.children[ offset ];
			if( next == null ) return false;
			else{
				current = next;
			}
		}
		if( current == null ) return false;
		else return current.isOccupied();
	}

	public static TrieNode loadDictionary(String filePath) throws IOException{
		File f = new File(filePath);
		BufferedReader br = new BufferedReader( new FileReader(f) );
		TrieNode root = new TrieNode();
		String fileLine = null;
		while( (fileLine = br.readLine()) != null ){
			root.insert(fileLine);
		}
		return root;
	}

}
