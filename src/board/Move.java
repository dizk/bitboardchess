package board;

public class Move {
	

	private int to;
	private int from;
	private int type;
	private Move extraMove = null;
	
	public Move(int from, int to, int type){
		this.from = from;
		this.to = to;
		this.type = type;
	}
	
	public Move(int from, int to, int type, Move move){
		this.from = from;
		this.to = to;
		this.type = type;
		this.extraMove = move;
	}

	
	public int getTo(){
		return to; 
	}
	
	public int getFrom(){
		return from; 
	}
	
	public int getType(){
		return type;
	}
	
	public boolean hasExtraMove(){
		if (extraMove == null){
			return false;
		} else {
			return true;
		}
	}
	
	public Move getExtraMove(){
		return extraMove;
	}
	
	

	@Override
	public String toString() {
		return "Move [from=" + from + ", to=" + to + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + from;
		result = prime * result + to;
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (from != other.from)
			return false;
		if (to != other.to)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
}
