package com.jpush.protocal.push;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.jpush.im.protocal.common.Command;
import com.jpush.im.protocal.utils.ProtocolUtil;
import com.jpush.protocal.im.request.BaseRequest;

public class PushLoginRequest extends BaseRequest {
	private int cmd = 1;  //  push login command
	private PushLoginRequestBean content;
	public PushLoginRequest(int version, long rid, int sid, long juid, PushLoginRequestBean bean) {
		super(version, rid, sid, juid);
		this.command = Command.KKPUSH_LOGIN.COMMAND;
		this.content = bean;
	}

	@Override
	public void buidRequestBody() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try{
			bos.write(ProtocolUtil.intToByteArray(this.cmd, 1));  
			bos.write(ProtocolUtil.copyArray(content.getFrom_resource().getBytes(), 4)); 
			this.writeTLV2(bos, content.getPasswdmd5());
			bos.write(ProtocolUtil.intToByteArray(content.getClient_version(), 4));
			this.writeTLV2(bos, content.getAppkey());
			bos.write(ProtocolUtil.intToByteArray(content.getPlayform(), 1));
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
