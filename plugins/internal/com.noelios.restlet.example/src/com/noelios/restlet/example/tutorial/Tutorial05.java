/*
 * Copyright 2005-2006 Noelios Consulting.
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * http://www.opensource.org/licenses/cddl1.txt
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * http://www.opensource.org/licenses/cddl1.txt
 * If applicable, add the following below this CDDL
 * HEADER, with the fields enclosed by brackets "[]"
 * replaced with your own identifying information:
 * Portions Copyright [yyyy] [name of copyright owner]
 */

package com.noelios.restlet.example.tutorial;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.component.Container;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;

/**
 * Restlets containers.
 * @author Jerome Louvel (contact@noelios.com) <a href="http://www.noelios.com/">Noelios Consulting</a>
 */
public class Tutorial05
{
   public static void main(String[] args) throws Exception
   {
      // Create a new Restlet container and add a HTTP server connector to it
   	Container myContainer = new Container();
      myContainer.getServers().add(Protocol.HTTP, 8182);

      // Create a new Restlet that will display some path information.
      Restlet myRestlet = new Restlet()
         {
            public void handleGet(Request request, Response response)
            {
               // Print the requested URI path
               String output = "Resource URI:  " + request.getResourceRef() + '\n' +
                               "Base URI:      " + request.getBaseRef() + '\n' +
                               "Relative path: " + request.getRelativePart() + '\n' +
                               "Query string:  " + request.getResourceRef().getQuery();
               response.setOutput(output, MediaType.TEXT_PLAIN);
            }
         };

      // Then attach it to the local host
      myContainer.getLocalHost().attach("/trace", myRestlet);

      // Now, let's start the container!
      // Note that the HTTP server connector is also automatically started.
      myContainer.start();
   }

}
