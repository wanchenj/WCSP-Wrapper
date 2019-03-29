package HelloWorld;

public class sample {


	native void helloFromC();

	public sample()
	{
		// do nothing constructor
	}

	static public void main(String argv[]){
		System.loadLibrary("ctest");
		sample sam_1 = new sample();
		sam_1.helloFromC();
	
	}
}
