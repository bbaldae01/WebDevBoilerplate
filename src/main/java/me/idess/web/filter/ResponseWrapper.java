package me.idess.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper {
	private final String	body;
	
	public ResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		OutputStream outputStream = response.getOutputStream();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream.writeTo(outputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		body = new String(byteArray, StandardCharsets.UTF_8);
	}
	
	@Override
	public ServletOutputStream getOutputStream() {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			byteArrayOutputStream.write(body.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletOutputStream servletOutputStream = new ServletOutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				// TODO Auto-generated method stub
				byteArrayOutputStream.write(b);
			}
			
			@Override
			public void setWriteListener(WriteListener writeListener) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		return servletOutputStream;
	}
	
	public String getBody() {
		return body;
	}
}
