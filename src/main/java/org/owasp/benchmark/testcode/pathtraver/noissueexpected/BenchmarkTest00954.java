/**
* OWASP Benchmark Project v1.2
*
* This file is part of the Open Web Application Security Project (OWASP)
* Benchmark Project. For details, please see
* <a href="https://www.owasp.org/index.php/Benchmark">https://www.owasp.org/index.php/Benchmark</a>.
*
* The OWASP Benchmark is free software: you can redistribute it and/or modify it under the terms
* of the GNU General Public License as published by the Free Software Foundation, version 2.
*
* The OWASP Benchmark is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
* even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* @author Dave Wichers <a href="https://www.aspectsecurity.com">Aspect Security</a>
* @created 2015
*/

package org.owasp.benchmark.testcode.pathtraver.noissueexpected;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/pathtraver-01/BenchmarkTest00954")
public class BenchmarkTest00954 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		javax.servlet.http.Cookie userCookie = new javax.servlet.http.Cookie("BenchmarkTest00954", "FileName");
		userCookie.setMaxAge(60*3); //Store cookie for 3 minutes
		userCookie.setSecure(true);
		userCookie.setPath(request.getRequestURI());
		response.addCookie(userCookie);
		javax.servlet.RequestDispatcher rd = request.getRequestDispatcher("/pathtraver-01/BenchmarkTest00954.html");
		rd.include(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	
		javax.servlet.http.Cookie[] theCookies = request.getCookies();
		
		String param = "noCookieValueSupplied";
		if (theCookies != null) {
			for (javax.servlet.http.Cookie theCookie : theCookies) {
				if (theCookie.getName().equals("BenchmarkTest00954")) {
					param = java.net.URLDecoder.decode(theCookie.getValue(), "UTF-8");
					break;
				}
			}
		}

		String bar = new Test().doSomething(request, param);
		
		String fileName = null;
		java.io.FileOutputStream fos = null;

		try {
			// Create the file first so the test won't throw an exception if it doesn't exist.
			// Note: Don't actually do this because this method signature could cause a tool to find THIS file constructor 
			// as a vuln, rather than the File signature we are trying to actually test.
			// If necessary, just run the benchmark twice. The 1st run should create all the necessary files.
			//new java.io.File(org.owasp.benchmark.helpers.Utils.testfileDir + bar).createNewFile();
			
			fileName = org.owasp.benchmark.helpers.Utils.testfileDir + bar;
	
	
	        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(fileName);
	        java.io.FileDescriptor fd = fileInputStream.getFD();
	        fos = new java.io.FileOutputStream(fd);
	        response.getWriter().println(
			"Now ready to write to file: " + org.owasp.esapi.ESAPI.encoder().encodeForHTML(fileName)
);

		} catch (Exception e) {
			System.out.println("Couldn't open FileOutputStream on file: '" + fileName + "'");
//			System.out.println("File exception caught and swallowed: " + e.getMessage());
		} finally {
			if (fos != null) {
				try {
					fos.close();
                    fos = null;
				} catch (Exception e) {
					// we tried...
				}
			}
		}
	}  // end doPost

	
    private class Test {

        public String doSomething(HttpServletRequest request, String param) throws ServletException, IOException {

		String bar = "safe!";
		java.util.HashMap<String,Object> map9749 = new java.util.HashMap<String,Object>();
		map9749.put("keyA-9749", "a_Value"); // put some stuff in the collection
		map9749.put("keyB-9749", param); // put it in a collection
		map9749.put("keyC", "another_Value"); // put some stuff in the collection
		bar = (String)map9749.get("keyB-9749"); // get it back out
		bar = (String)map9749.get("keyA-9749"); // get safe value back out

            return bar;
        }
    } // end innerclass Test

} // end DataflowThruInnerClass
