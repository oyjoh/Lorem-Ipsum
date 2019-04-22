package inf112.skeleton.app.Netcode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

	private Client client;

	public ClientHandler(Client client) {
		this.client = client;
	}

	private void writeOut(ChannelHandlerContext ctx){
		Scanner in = new Scanner(System.in);
		String msg = in.nextLine();
		ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
		String inString = in.toString(CharsetUtil.UTF_8);

		switch (inString){
			case "HOST_DONE":
				//TODO
				break;
			case "HOST_READY":

				break;

			default:
				System.err.println(inString + " has no handling");
		}

		writeOut(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("CLIENT CONNECTED");

		writeOut(ctx);
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
