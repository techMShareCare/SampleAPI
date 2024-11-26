package samples.Webhooks.CreateNewWebhooks;

import java.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.math.BigDecimal;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;
import Model.*;

public class StoreOAuthCredentials {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		run(null, null, null);
	}

	public static void run(String vCcorrelationId, String vCsenderOrganizationId, String vCpermissions) {
	
		SaveSymEgressKey requestObj = new SaveSymEgressKey();

		requestObj.clientRequestAction("STORE");
		Kmsegressv2keyssymKeyInformation keyInformation = new Kmsegressv2keyssymKeyInformation();
		keyInformation.provider("<INSERT ORGANIZATION ID HERE>");
		keyInformation.tenant("nrtd");
		keyInformation.keyType("oAuthClientCredentials");
		keyInformation.organizationId("<INSERT ORGANIZATION ID HERE>");
		keyInformation.clientKeyId("client username");
		keyInformation.key("client secret");
		keyInformation.expiryDuration("365");
		requestObj.keyInformation(keyInformation);

		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CreateNewWebhooksApi apiInstance = new CreateNewWebhooksApi(apiClient);
			apiInstance.saveSymEgressKey(vCsenderOrganizationId, vCpermissions, vCcorrelationId, requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			WriteLogAudit(Integer.parseInt(responseCode));
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}