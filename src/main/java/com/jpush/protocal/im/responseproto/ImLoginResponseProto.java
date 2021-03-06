package com.jpush.protocal.im.responseproto;

import com.jpush.im.protobuf.Im;
import com.jpush.im.protobuf.Im.Protocol;
import com.jpush.im.protobuf.Im.ProtocolBody;

public class ImLoginResponseProto extends BaseProtobufResponse {

	public ImLoginResponseProto(Protocol protocol) {
		super(protocol);
	}

	@Override
	protected void buildResposneBody() {
		Im.Response.Builder responseBuilder = Im.Response.newBuilder();
		responseBuilder.setCode(this.getCode());
		responseBuilder.setMessage(this.getMessage());
		
		Im.ProtocolBody body = this.protocol.getBody();
		body = ProtocolBody.newBuilder(body).setCommonRep(responseBuilder).build();
		protocol = Protocol.newBuilder(protocol).setBody(body).build();
	}
	
}
