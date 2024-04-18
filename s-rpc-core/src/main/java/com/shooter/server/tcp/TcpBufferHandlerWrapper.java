package com.shooter.server.tcp;

import com.shooter.protocol.ProtocolConstant;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;


/**
 * ClassName: TcpBufferHandlerWrapper
 * Package: com.shooter.server.tcp
 * Description:
 * @Author:Shooter
 * @Create 2024/4/18 8:04
 * @Version 1.0
 */
public class TcpBufferHandlerWrapper implements Handler<Buffer> {

    /**
     * 解析器，解决半包粘包
     */
    private final RecordParser recordParser;

    public TcpBufferHandlerWrapper(Handler<Buffer> bufferHandler) {
        this.recordParser = initRecordParser(bufferHandler);
    }

    @Override
    public void handle(Buffer buffer) {
        recordParser.handle(buffer); // 解决粘包半包
    }

    private RecordParser initRecordParser(Handler<Buffer> bufferHandler) {
        // 构造parser
        RecordParser parser = RecordParser.newFixed(ProtocolConstant.MESSAGE_HEADER_LENGTH); // 先构建请求头长度17的parser
        parser.setOutput(new Handler<Buffer>() {
            // 初始化
            int size = -1;
            Buffer resultBuffer = Buffer.buffer(); // 用来拼接结果集的buffer
            @Override
            public void handle(Buffer buffer) {
                if (size == -1){ // resultBuffer 拼装请求头
                    size = buffer.getInt(13);
                    parser.fixedSizeMode(size);
                    resultBuffer.appendBuffer(buffer);
                }else{ // resultBuffer 拼装消息体
                    resultBuffer.appendBuffer(buffer);
                    // 已拼接为完整 Buffer，执行处理(外头传进来的handler)
                    // 解码buffer得到rpcRequest->根据rpcRequest封装的信息反射调用->封装成rpcResponse->封装成ProtocolMessage编码后响应出去
                    bufferHandler.handle(resultBuffer);
                    // 重置
                    size = -1;
                    parser.fixedSizeMode(ProtocolConstant.MESSAGE_HEADER_LENGTH);
                    resultBuffer = Buffer.buffer();
                }
            }
        });

        return parser;
    }
}
