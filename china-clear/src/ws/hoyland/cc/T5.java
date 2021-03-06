package ws.hoyland.cc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.Random;

import org.json.JSONObject;

public class T5 {

	public T5() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BigInteger root = new BigInteger("2");
		BigInteger d = new BigInteger("B8008767A628A4F53BCB84C13C961A55BF87607DAA5BE0BA3AC2E0CB778E494579BD444F699885F4968CD9028BB3FC6FA657D532F1718F581669BDC333F83DC3", 16);

		//generate random e
		byte[] bs = new byte[14];
		Random r = new Random();
		
		bs[0] = (byte)(Math.abs(r.nextInt()) % 64);
		for(int i=0;i<bs.length;i++){
			bs[i] = (byte)(Math.abs(r.nextInt()) % 256);
		}
		
		BigInteger e = new BigInteger(bs);
		
		//generate my crypt-pub-key
		String fcpk = root.modPow(e, d).toString(16).toUpperCase();
		
		//System.out.println(fcpk);
		StringBuffer sb = new StringBuffer();
		try{
			URL url = new URL("http://w.aq.qq.com/cn/mbtoken3/mbtoken3_exchange_key_v2?mobile_type=4&client_type=2&client_ver=15&local_id=0&config_ver=100&pub_key="+fcpk+"&sys_ver=2.2");
			InputStream in = url.openStream();
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line=bin.readLine())!=null){
				sb.append(line);
				//System.out.println(line);
			}
			bin.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		JSONObject json = new JSONObject(sb.toString());
		String sid = json.getString("sess_id");
		String tcpk = json.getString("pub_key"); //get server's crypt pub key
		//System.out.println(tcpk);
		
		//caculate the key for TEA alth
		BigInteger btcpk = new BigInteger(tcpk, 16);
		String sk = btcpk.modPow(e, d).toString(16).toUpperCase();
		byte[] key = Converts.MD5Encode(Converts.hexStringToByte(sk));		
		//System.out.println(key.length);
		
		//crypt request data
		String uin = "744625551";	//要绑定的其他QQ号
		String token = "1406087124841854";	//手机上的令牌
		String password = "981019.*";	//要绑定的其他QQ号密码
		//System.out.println(Converts.MD5EncodeToHex("123456"));
		json = new JSONObject();
		json.put("tkn_seq", token);
		json.put("password", Converts.MD5EncodeToHex(password));
//		json.put("mobile_code", "123456");
		//System.out.println(json.toString());
		byte[] array = json.toString().getBytes();
		
		Crypter crypter = new Crypter();
		byte[] bb = crypter.encrypt(array, key);
		String data = Converts.bytesToHexString(bb);
		//System.out.println(data);
		
		sb = new StringBuffer();
		try{
			URL url = new URL("http://w.aq.qq.com/cn/mbtoken3/mbtoken3_upgrade_determin_v2?uin="+uin+"&sess_id="+sid+"&data="+data);
			System.out.println(url.toString());
			
//			InputStream in = url.openStream();
//			BufferedReader bin = new BufferedReader(new InputStreamReader(in));
//			String line = null;
//			while((line=bin.readLine())!=null){
//				sb.append(line);
//				//System.out.println(line);
//			}
//			bin.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		//System.out.println(sb.toString());
	}

}
