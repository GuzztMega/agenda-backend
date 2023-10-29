package br.com.guzzmega.agenda.utils;

public class DocumentUtils {

    public static boolean isValidCPF(String document) throws IllegalArgumentException {
        try
        {
            if (document == null || document.length() != 11 || document.equals("00000000000")) {
                return false;
            }

            int d1, d2;
            int digit1, digit2, mod;
            int CPFdigit;
            String verifierResult;

            d1 = d2 = 0;
            digit1 = digit2 = mod = 0;

            for (int nCount = 1; nCount < document.length() - 1; nCount++) {
                CPFdigit = Integer.valueOf(document.substring(nCount - 1, nCount)).intValue();
                d1 = d1 + (11 - nCount) * CPFdigit;
                d2 = d2 + (12 - nCount) * CPFdigit;
            };

            mod = (d1 % 11);
            if (mod < 2) {
                digit1 = 0;
            } else {
                digit1 = 11 - mod;
            }
            d2 += 2 * digit1;
            mod = (d2 % 11);

            if (mod < 2) {
                digit2 = 0;
            } else {
                digit2 = 11 - mod;
            }

            String verifier = document.substring(document.length() - 2, document.length());
            verifierResult = String.valueOf(digit1) + String.valueOf(digit2);

            return verifier.equals(verifierResult);

        } catch (Exception ex)
        {
            return false;
        }
    }

}
