package until.http;

import Handle.Accept;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpInboundHandler extends SimpleChannelInboundHandler<String> {

    private String Result;
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        File dir = new File("g:\\mypic");
//        if(!dir.exists()){
//            dir.mkdir();//文件夹不存在,创建mypic文件夹
//        }
//
//        //我觉得这里的后缀名，需要通过发送方也发过来的
//        File file = new File(dir, ".jpg");
//
//        FileOutputStream fout = new FileOutputStream(file);
//
//        //从socket流中读取数据，存储到本地文件。相当于对拷
//        byte[] buf = new byte[1024];
//
//
//        //向客户端发送回馈信息
//        OutputStream out = s.getOutputStream();
//        out.write( "上传成功".getBytes() );
//
//        fout.close();
//        s.close();

        //读去
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        int len=0;
//        while( (len=ctx.read(result))!=-1){
//            fout.write(buf, 0, len);
//        }
        //图片接收完毕
        String resultStr = new String(result1);
        String[] a=resultStr.split("\\|");

        Channel ct= ctx.pipeline().channel();

        Accept accept =new Accept(a, ct);
        Result=accept.loginRequst();


        //写入
        String response =Result ;
        ByteBuf encoded =  ctx.alloc().buffer(response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();

    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }
}
