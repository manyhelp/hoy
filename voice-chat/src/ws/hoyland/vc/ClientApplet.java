package ws.hoyland.vc;

import java.applet.Applet;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientApplet extends Applet {
	public ClientApplet() {
	}

	private Player player;
	private Receiver receiver;
	private Sender sender;
	private Selector selector;
	private SocketChannel sc;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3481124422734904591L;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("init...");
		try{
			player = new Player();
			new Thread(player).start();
			
			selector = Selector.open();
			receiver = new Receiver(selector, player);
			new Thread(receiver).start();
			
			SocketAddress sa = new InetSocketAddress("127.0.0.1", 8000);
			sc = SocketChannel.open();
			sc.configureBlocking(false);
			//System.out.println("c1");
			sc.connect(sa);
			//System.out.println("c2");
			//System.err.println("getReceiveBufferSize:"+dc.socket().getReceiveBufferSize());
			//dc.socket().setReceiveBufferSize(arg0)
			receiver.wakeup();
			selector.wakeup();
			sc.register(selector, SelectionKey.OP_CONNECT);
			receiver.sleep();
			//System.out.println("c3");
			sender = new Sender(sc);
			new Thread(sender).start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		sender.stop();
		receiver.stop();
		player.stop();
		
		if(sc!=null){
			try{
				sc.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}