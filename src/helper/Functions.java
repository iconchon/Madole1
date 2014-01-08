package helper;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Functions {
	
	public Functions(){
		
	}
	public static String Convert(String time){
		
		long dv = Long.valueOf(time)*1000;// its need to be in milisecond
		Date df = new java.util.Date(dv);
		String show = new SimpleDateFormat("dd-MM-yyyy hh:mma").format(df);
		return show;
	}
	
	public static  String timecv (long uptimeMilis){
		int seconds, minutes, hours;
		seconds = (int) (uptimeMilis / 1000);
		hours   = seconds / 3600;
		minutes = seconds / 60;
		seconds = seconds % 60;
		if (seconds < 10) {
			System.out.println("=" + hours +":"+ minutes + ":0" + seconds);
		}else {
			System.out.println("=" + hours +":"+ minutes + ":" + seconds);
		}
		return null;
		
	}
	
	public static String selisih (long mulai, long akhir){
		
		String runtime = null;
		int detik,menit,jam;
		
		long selisih = (int)(akhir-mulai);
		int sek = (int)(selisih%1000);
		detik = (int)(selisih/1000);
		menit = detik/60;
		detik = detik%60;
		jam   = menit/60;
		menit = menit%60;

		if (jam>1) {
			runtime = "["+selisih+"] = "+jam+ ":" +menit+ ":" + detik +":" +sek+" mili sekon";
		}else{
			runtime = menit + ":" + detik + "mili sekon";
		}
		
		System.out.println(runtime);
		return runtime;
		
		
	}

}
