package com.test.spring.boot.blog.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class XheditorImgUpload {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String imgUpload(HttpServletRequest request,
			@RequestParam MultipartFile filedata) {

		String filedataFileName = filedata.getOriginalFilename();
		
		String path = request.getSession().getServletContext()
				.getRealPath("imgupload/");

		String newFileName = UUID.randomUUID().toString()
				+ filedataFileName.substring(filedataFileName.indexOf("."),
						filedataFileName.length());
		String message;
		String err = "";
		String msg = "/imgupload/" + newFileName;

		try {
			uploadFile(filedata.getBytes(), path, newFileName);
		} catch (Exception e) {
		    err = e.getMessage();
		}
		
		message = "{\"err\":\"" + err + "\",\"msg\":\"" + msg + "\"}";
		err = message;
		return message;
	}
	
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception { 
        File targetFile = new File(filePath);  
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filePath+fileName));
        bos.write(file);
        bos.flush();
        bos.close();
    }
}
