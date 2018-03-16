package com.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ResponseUtil {
	public static void outHtml(String string, HttpServletResponse response) {
		responseHtml(string, response, "UTF-8");
	}

	public static void responseHtml(String string,
			HttpServletResponse response, String coding) {
		try {
			if (coding == null || coding.length() == 0)
				coding = "UTF-8";
			response.setContentType("text/html");
			response.setCharacterEncoding(coding);
			PrintWriter out = response.getWriter();
			out.write(string);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Writer outXml(String string, HttpServletResponse response)
			throws Exception {
		return responseXml(string, response, "UTF-8");
	}

	public static Writer responseXml(String string,
			HttpServletResponse response, String coding) throws Exception {
		if (coding == null || coding.length() == 0)
			coding = "UTF-8";
		response.setContentType("text/xml");
		response.setCharacterEncoding(coding);
		PrintWriter out = response.getWriter();
		out.write(string);
		out.flush();
		return out;
	}

	public static void outJsonCache(String jsonString,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.close();
	}

	public static void outJson(String jsonString, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.close();
	}

	public static void outTxt(HttpServletResponse response, String txt,
			String fileName) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("text/plain");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\";");
			PrintWriter pw = response.getWriter();
			pw.println(txt);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void outScv(HttpServletResponse response, HSSFWorkbook hwb,
			String fileName) throws IOException{
		response.setContentType("application/vnd.ms-excel MSEXCEL");
		response.setHeader("Content-disposition", "attachment;filename="
				+ URLEncoder.encode(fileName,"UTF-8"));
		OutputStream out = response.getOutputStream();
		try {
			hwb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

	public static void jsRedirect(String path, HttpServletResponse response) {
		outHtml("<script>window.location = '" + path + "'</script>", response);
	}
}
