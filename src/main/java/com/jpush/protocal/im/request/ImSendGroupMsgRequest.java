package com.jpush.protocal.im.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.jpush.im.protobuf.Im.Protocol;
import com.jpush.im.protocal.common.Command;
import com.jpush.im.protocal.utils.ProtocolUtil;

public class ImSendGroupMsgRequest extends BaseRequest {
	private Protocol protobuf;
	public ImSendGroupMsgRequest(int version, long rid, int sid, long juid, Protocol protobuf) {
		super(version, rid, sid, juid);
		this.command = Command.JPUSH_IM.COMMAND;
		this.protobuf = protobuf;
	}

	@Override
	public void buidRequestBody() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try{
			bos.write(this.protobuf.toByteArray());
			this.mBody = bos.toByteArray();
		} catch (Exception e) {
			try {
				bos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
