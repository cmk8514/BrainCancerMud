package com.planet_ink.coffee_mud.Libraries.interfaces;

/*
   Copyright 2005-2017 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
public interface TextEncoders extends CMLibrary
{
	public String decompressString(byte[] b);
	public byte[] compressString(String s);
	public boolean checkPasswordAgainstRandomHashString(final String passwordString, final String hashString);
	public boolean isARandomHashString(final String password);
	public boolean passwordCheck(final String pass1, final String pass2);
	public boolean checkHashStringPairs(final String hashString1, final String hashString2);
	public String makeRandomHashString(final String password);
	public String generateRandomPassword();
}
