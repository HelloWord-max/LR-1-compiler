package RubbishComplier;

public class Token {
	String TokenName="";
	String TokenCode="";
	int TokenRow=-1;
	int TokenColumn=-1;
public String getTokenName() {
		return TokenName;
	}
	public int getTokenColumn() {
	return TokenColumn;
}
public void setTokenColumn(int tokenColumn) {
	TokenColumn = tokenColumn;
}
	public int getTokenRow() {
	return TokenRow;
}
public void setTokenRow(int tokenRow) {
	TokenRow = tokenRow;
}
	public void setTokenName(String tokenName) {
		TokenName = tokenName;
	}
	public String getTokenCode() {
		return TokenCode;
	}
	public void setTokenCode(String tokenCode) {
		TokenCode = tokenCode;
	}

public Token() {
	// TODO Auto-generated constructor stub
}
}
