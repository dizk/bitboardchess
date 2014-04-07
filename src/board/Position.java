package board;

public class Position {
	private int rank;
	private int file;

	public Position(int file, int rank) {
		this.rank = rank;
		this.file = file;
	}
	
	public int getRank(){
		return this.rank;
	}
	
	public int getFile(){
		return this.file;
	}

	public String toString() {
		String s = "";

		switch (this.file) {
		case 0:
			s += "a";

			break;
		case 1:
			s += "b";

			break;
		case 2:
			s += "c";

			break;
		case 3:
			s += "d";

			break;
		case 4:
			s += "e";

			break;
		case 5:
			s += "f";

			break;
		case 6:
			s += "g";

			break;
		case 7:
			s += "h";

			break;
		default:
			break;

		}

		switch (this.rank) {
		case 0:
			s += "8";

			break;
		case 1:
			s += "7";

			break;
		case 2:
			s += "6";

			break;
		case 3:
			s += "5";

			break;
		case 4:
			s += "4";

			break;
		case 5:
			s += "3";

			break;
		case 6:
			s += "2";

			break;
		case 7:
			s += "1";

			break;
		default:
			break;
		}

		return s;
	}
}