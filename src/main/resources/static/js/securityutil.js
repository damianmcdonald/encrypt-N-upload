var securityUtil = new function() {

	var phrases = [
		"nm5DQwEe9PeQS?zkGv@+UYc2xNbpq+@Grz!ny9H@@AZJ@dPK&GJPmgYBH7&Jc-Uj",
		"bK!NStKy$VeWcHjPB-hWe_MfBVx3mBGDwgmb#Gsp?2^89Wc?_+64YWdDYLE@_Ef7",
		"*8RfKWt5R=!c#k+SeBrwKRg2GwKkM_u99!vHf84Qh4B#D4tjkKJQGvasLg_zs_&%",
		"=*WeBGbXz=^ZnEN_bUHY7PF^35dnsth&XAw=svPKPP?bP%QhK%Gs_^x93bjfXmSc",
		"B8AUWFU!f56kyFfpR^Kd_JMGmynyV?9K-!m!W$!Q?$JGR2ZM9e4_pq4R3+RZK+pc",
		"ehv#_3VNrEEr+C+HLwgt8y83^b6nttF!zr8JyrajCc9KmyAHM5F=HC&F=DFcBFuE",
		"?2ft&_qPpwjq_Ubj%FUSc_Tr+d=%%P*zJFDE@&pEVyM$hhkp24KNCJ7tnFkfZwR5",
		"2dMb8eg%U4%N5VYY!HRsAUDveAFp8^!sJ5$hs_Ctp^kR7U^Vfs=n8q?9&JHe%J5L",
		"V9^&Htf7U%G3kw*47pFGvj@kDcNvwTmUkJ-CEJw48=kd98PF*&@tvhz9v3=HGuyD",
		"jvz3mhGf7CZAA#yZrcz%5b*!d*jZ%ZSUr42Nx3W$9kn&4F*GwC+LJ$&!AJPD7_U9",
		"&m$4v^K*JYtGFree5%%T&9xZMUCu4zMHwutxbf*PQSDG6TgwfS7hyrPEw-wQs9aV",
		"=Jq^mD+znaavTe83EvRw&X*-Fh4P*539W*5K3wq-pL7U&M9nPuVdXuB6kbM#njTD",
		"HeyZkMaU@xgZ7SPsfNh*N?Ba8z7fQ!QVJc-j!uKyDAM_M5U6CURt?sc@ndD6t!+2",
		"2PDv+cJ4ML-gfxGqEZjzvAE?H$X#_XTB9DnZCnDb?jHeKH6qxw7z58&tTyHTDc^Q",
		"?gBCe!ArCHMFKSvhc%UKj2ywsKh=4#F4cnJ+SSph43GbVn&xTDaMqAa+6yUy*kFc",
		"HZ+^R4UPN$HP55rUXSQ=vcB%vB?ar+hhAvjvyF!Uuf7#j!Y=_Pm86kpkXcbnCcZ9",
		"73jD#Ub@ryBtdqe3BMdTUJ@AVYYUE5A%zy4G+5v%EqhU=U4eyRhphzJ_DkZs$MmW",
		"yG3VsJ_Sjg!TkuFKyL7f?t$=QKA=SCGAU@nC+jfaL6peKz-c4SYERh!!&prH**pK",
		"_KzndBgq&+jxRhxLAm6dFs9AHU6##SQPgECTB$3PyV97a%*L%yS6!K?F9%gGk4y6",
		"shm%a7g##_yZjtK%VdF#?HhQsqdW?Bwz84uCc2GeunBLKtHu3^vy&qFf8SxQkQv%",
		"z_qcGxEzkLjLT94U_=4GWuTex9n+&Q-X8894QA%5U&m-_fNYgDLN-9=F!3gycK^L",
		"4Jpxv3V^ze!Td!4Kyym7dVsH3e4n--ju_w7G=rYxF2ADws9-#_*Bfu3w?6MC82-N",
		"e=G+p7brgYtU6*5rc6LRs&mD!RawdWQpvuZ$C799Kw4**!WZ2mY6yVjmb@DPejw7",
		"2m$*7z_R2@!nZW5kUfxQU^HLdnc_Uf+ujeCja6&7nF5^+gKzuAEAEW*EUfjW$U#s",
		"2_EH$zZV3M^Be4vHvQGLtVBckHWm5ZRH348TZq9QN=+Z_*_eH!J?Lbm##Ej3=LDS",
		"ze5eQtM4CPKTzcdR=6^f3QVRyF*j5rBv8!p5UChpJFs!7YesNMmRg&hvG4D&qDA#",
		"CVGC5wYjkuY6!GY^yRzDVRYBXy#s5AA-^*!QC9+5w_kGyGXpR*#t9&CJacba*jeC",
		"kh@Q?HKYzgX$PvWWXz%VSe$DAER!bp9_^U!6tj_fWY3tjvg^5W#&HQW*wS!j&T=9",
		"G7#^a=C*RTDm_u?LXeY8Yb5b3UBGTrd@T3!&Z!wkfwRc-ZWf9@?_kEh^wFDYr$Dt",
		"Rjc6qeJmTkHe49mthe&YML@A&EJkv6zV=A+G6jvgW7ucL%?nk@LQ*AF7#p?58UEA"
	];

	var letterCipher = [
		"n4", "zZ", "3X", "LP", "Yq", "Vx", "Hm", "z2", "GR", "W9",
		"8@", "$L", "@g", "76", "@@", "Lt", "+J", "Cx", "&P", "Ps",
		"@7", "L$", "rD", "*2", "*s", "n+", "W^", "X=", "4#", "!@"
	];

	function getPassphrase() {
       function getRandom(min, max) {
		    var rand = Math.random() * (max - min) + min;
		    return Math.floor(rand);
		}

		var rand1 = getRandom(0, phrases.length-1);
		var rand2 = getRandom(0, phrases.length-1);
		var rand3 = getRandom(0, phrases.length-1);
		var rand4 = getRandom(0, phrases.length-1);
		var rand5 = getRandom(0, phrases.length-1);
		var rand6 = getRandom(0, phrases.length-1);
		var rand7 = getRandom(0, phrases.length-1);
		var rand8 = getRandom(0, phrases.length-1);
		var rand9 = getRandom(0, phrases.length-1);
		var rand10 = getRandom(0, phrases.length-1);

		var phrases1 = phrases[rand1];
		var phrases2 = phrases[rand2];
		var phrases3 = phrases[rand3];
		var phrases4 = phrases[rand4];
		var phrases5 = phrases[rand5];
		var phrases6 = phrases[rand6];
		var phrases7 = phrases[rand7];
		var phrases8 = phrases[rand8];
		var phrases9 = phrases[rand9];
		var phrases10 = phrases[rand10];

		var part1 = phrases1.substring(0, 13);
		var part2 = phrases2.substring(14, 26);
		var part3 = phrases3.substring(29, 45);
		var part4 = phrases4.substring(49, 61);
		var part5 = phrases5.substring(22, 41);
		var part6 = phrases6.substring(3, 13);
		var part7 = phrases7.substring(51, 62);
		var part8 = phrases8.substring(11, 19);
		var part9 = phrases9.substring(18, 31);
		var part10 = phrases10.substring(7, 23);

		var passphrase = part1 + part2 + part3 + part4 + part5 + part6 + part7 + part8 + part9 + part10;

		return { 
				"passphrase": passphrase, 
				"parts": [
					letterCipher[rand1], 
					letterCipher[rand2], 
					letterCipher[rand3], 
					letterCipher[rand4],
					letterCipher[rand5],
					letterCipher[rand6],
					letterCipher[rand7],
					letterCipher[rand8],
					letterCipher[rand9],
					letterCipher[rand10]
				] 
		};
    }

    function encryptValue(valueToEncrypt) {
		var iterationCount = 1000;
	    var keySize = 128;

	    function arrayToString(arr) {
	        var asString = "";
	        for(var i=0; i<arr.length; i++) {
	            if(i == arr.length-1) {
	                asString += arr[i];
	            } else {
	                asString += arr[i] + ","
	            }
	        }
	        return asString;
	    }

	    var passphrase = getPassphrase();
	    var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
	    var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
	    var aesUtil = new AesUtil(keySize, iterationCount);
		var ciphertext = aesUtil.encrypt(salt, iv, passphrase.passphrase, valueToEncrypt);

		var encryptObj = {
			"cipherText": ciphertext, 
	        "passphraseParts": arrayToString(passphrase.parts), 
	        "salt": salt,
	        "iv": iv
		}

		return encryptObj;
    }

    this.encryptCredentials = function (userName, password) {
		var userNameEncryptObj = encryptValue(userName);
		var passwordEncryptObj = encryptValue(password);

	    var aesObj = {
	        "parama": userNameEncryptObj.cipherText, 
	        "paramz": userNameEncryptObj.passphraseParts, 
	        "paramb": passwordEncryptObj.cipherText, 
	        "paramy": passwordEncryptObj.passphraseParts,
	        "paramc": userNameEncryptObj.salt,
	        "paramx": userNameEncryptObj.iv,
	        "paramd": passwordEncryptObj.salt,
	        "paramw": passwordEncryptObj.iv
	    }
	    return aesObj
    };
}

