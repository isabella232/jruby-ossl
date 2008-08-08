/***** BEGIN LICENSE BLOCK *****
 * Version: CPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Common Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/cpl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2008 Ola Bini <ola.bini@gmail.com>
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the CPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the CPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/
package org.jruby.ext.openssl.impl;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Set;
import javax.crypto.Cipher;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.pkcs.SignerInfo;

/**
 * @author <a href="mailto:ola.bini@gmail.com">Ola Bini</a>
 */
public abstract class PKCS7Data {
    public abstract int getType();

    public Object ctrl(int cmd, Object v, Object ignored) {
        // TODO: Error
        return Integer.valueOf(0);
    }

    public Envelope getEnveloped() {
        return null;
    }

    public SignEnvelope getSignedAndEnveloped() {
        return null;
    }

    public Digest getDigest() {
        return null;
    }

    public Encrypt getEncrypted() {
        return null;
    }

    public ASN1Encodable getOther() {
        return null;
    }

    public void setSign(Signed sign) {
    }

    public Signed getSign() {
        return null;
    }

    public void setData(ASN1OctetString data) {
    }

    public ASN1OctetString getData() {
        return null;
    }

    public boolean isSigned() {
        return false;
    }

    public boolean isEncrypted() {
        return false;
    }

    public boolean isEnveloped() {
        return false;
    }

    public boolean isSignedAndEnveloped() {
        return false;
    }

    public boolean isData() {
        return false;
    }

    public boolean isDigest() {
        return false;
    }

    public boolean isOther() {
        return false;
    }

    public void setCipher(Cipher cipher) {
        throw new PKCS7Exception(PKCS7.F_PKCS7_SET_CIPHER,PKCS7.R_WRONG_CONTENT_TYPE);
    }

    public void addRecipientInfo(RecipInfo ri) {
        throw new PKCS7Exception(PKCS7.F_PKCS7_ADD_RECIPIENT_INFO,PKCS7.R_WRONG_CONTENT_TYPE);
    }

    public void addSigner(SignerInfoWithPkey psi) {
        throw new PKCS7Exception(PKCS7.F_PKCS7_ADD_SIGNER,PKCS7.R_WRONG_CONTENT_TYPE);
    }

    public void setContent(PKCS7 p7) {
        throw new PKCS7Exception(PKCS7.F_PKCS7_SET_CONTENT,PKCS7.R_WRONG_CONTENT_TYPE);
    }

    public Set<SignerInfoWithPkey> getSignerInfo() {
        return null;
    }

    public void addCertificate(X509Certificate cert) {
        throw new PKCS7Exception(PKCS7.F_PKCS7_ADD_CERTIFICATE,PKCS7.R_WRONG_CONTENT_TYPE);
    }

    public void addCRL(X509CRL crl) {
        throw new PKCS7Exception(PKCS7.F_PKCS7_ADD_CRL,PKCS7.R_WRONG_CONTENT_TYPE);
    }

    public static PKCS7Data fromASN1(Integer nid, DEREncodable content) {
        switch(nid) {
        case ASN1Registry.NID_pkcs7_data:
            return PKCS7DataData.fromASN1(content);
        case ASN1Registry.NID_pkcs7_signed:
            return PKCS7DataSigned.fromASN1(content);
        case ASN1Registry.NID_pkcs7_enveloped:
            return PKCS7DataEnveloped.fromASN1(content);
        case ASN1Registry.NID_pkcs7_signedAndEnveloped:
            return PKCS7DataSignedAndEnveloped.fromASN1(content);
        case ASN1Registry.NID_pkcs7_digest:
            return PKCS7DataDigest.fromASN1(content);
        case ASN1Registry.NID_pkcs7_encrypted:
            return PKCS7DataEncrypted.fromASN1(content);
        default:
            throw new UnsupportedOperationException("can't handle PKCS#7 with content type " + ASN1Registry.nid2ln(nid));
        }
    }
}// PKCS7Data
