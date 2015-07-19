package com.github.damianmcdonald.encryptnupload.service.impl;

import com.github.damianmcdonald.encryptnupload.service.CryptographyService;
import com.github.damianmcdonald.encryptnupload.util.AesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CryptographyServiceImpl implements CryptographyService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final static String[] PHRASES = new String[]{
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
    };

    private final static String[] LETTER_CIPHERS = new String[]{"n4", "zZ",
            "3X", "LP", "Yq", "Vx", "Hm", "z2", "GR", "W9", "8@", "$L", "@g",
            "76", "@@", "Lt", "+J", "Cx", "&P", "Ps", "@7", "L$", "rD", "*2",
            "*s", "n+", "W^", "X=", "4#", "!@"};

    @Override
    public String generateHash(final String textToHash)
            throws NoSuchAlgorithmException {
        // Create MessageDigest instance for MD5
        final MessageDigest md = MessageDigest.getInstance("MD5");
        // Add password bytes to digest
        md.update(textToHash.getBytes());
        // Get the hash's bytes
        final byte[] bytes = md.digest();
        // This bytes[] has bytes in decimal format;
        // Convert it to hexadecimal format
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        // Get complete hashed password in hex format
        return sb.toString();
    }

    @Override
    public String decrypt(final String textToDecrypt, final String[] phraseParts,
                          final String salt, final String iv)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException,
            InvalidAlgorithmParameterException {

        final int iterationCount = 1000;
        final int keySize = 128;
        final String passphrase = getPassphrase(phraseParts);
        final AesUtil aesUtil = new AesUtil(keySize, iterationCount);
        return aesUtil.decrypt(salt, iv, passphrase, textToDecrypt);
    }

    private String getPassphrase(final String[] phraseParts) {

        // build cipher
        final StringBuilder sb = new StringBuilder();

        final int part1 = findPositionInArray(phraseParts[0]);
        final int part2 = findPositionInArray(phraseParts[1]);
        final int part3 = findPositionInArray(phraseParts[2]);
        final int part4 = findPositionInArray(phraseParts[3]);
        final int part5 = findPositionInArray(phraseParts[4]);
        final int part6 = findPositionInArray(phraseParts[5]);
        final int part7 = findPositionInArray(phraseParts[6]);
        final int part8 = findPositionInArray(phraseParts[7]);
        final int part9 = findPositionInArray(phraseParts[8]);
        final int part10 = findPositionInArray(phraseParts[9]);

        sb.append(PHRASES[part1].substring(0, 13));
        sb.append(PHRASES[part2].substring(14, 26));
        sb.append(PHRASES[part3].substring(29, 45));
        sb.append(PHRASES[part4].substring(49, 61));
        sb.append(PHRASES[part5].substring(22, 41));
        sb.append(PHRASES[part6].substring(3, 13));
        sb.append(PHRASES[part7].substring(51, 62));
        sb.append(PHRASES[part8].substring(11, 19));
        sb.append(PHRASES[part9].substring(18, 31));
        sb.append(PHRASES[part10].substring(7, 23));

        return sb.toString();
    }

    private int findPositionInArray(String value) {
        int val = -1;
        for (int i = 0; i < LETTER_CIPHERS.length; i++) {
            if (LETTER_CIPHERS[i].equals(value)) {
                val = i;
                break;
            }
        }
        return val;
    }

}
