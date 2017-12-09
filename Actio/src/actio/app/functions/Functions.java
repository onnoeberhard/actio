package actio.app.functions;

import actio.app.R;
import actio.app.functions.inappbilling.IabHelper;
import actio.app.functions.inappbilling.IabHelper.QueryInventoryFinishedListener;
import actio.app.functions.inappbilling.IabResult;
import actio.app.functions.inappbilling.Inventory;
import actio.app.functions.inappbilling.Purchase;
import android.app.Activity;
import android.widget.Toast;

public class Functions {

	public static void disableAds(final Activity c) {
//		final String SKU = "actio.premium";
		final String SKU = "android.test.purchased";
		final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkTjNpPiDhu0HDyfZnwV2sF3F7kcUa95bD8XEvZ80yrWbYkQP/lOym5DCkh88KNCsZMp8jeU0FCCkOzpuOh+eEjBrEQvkmXU7fSziAzUs5AzNKciNCvPFJlYPM3vF3oUhoC/2rl1VJOemzT+hezyGD9jn5mvPyyvRTDE5+7wkLKQCf/P+knUVFbS5/gqHVMsZXLnX+NuizvQNqQdFqNBUJK/gmplLPZqfmHaDi9LmbYdKY6QnpB1BHXnqAll6Zeuk0X0CwaXLGhgDFVdoebhVGilIuc+8IsjQ8O2h9Rtog2lXA3j0neqGfm1dxW6rheJpqaIOI9igj8jNWxhveEk3iQIDAQAB";
		final IabHelper mHelper = new IabHelper(c, base64EncodedPublicKey);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				mHelper.launchPurchaseFlow(c, SKU, 10001, new IabHelper.OnIabPurchaseFinishedListener() {
					public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
						if (result.isFailure()) {
							mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
								public void onQueryInventoryFinished(IabResult result,
										Inventory inventory) {
									if (result.isFailure()) {
										Toast.makeText(c, R.string.error_sorry, Toast.LENGTH_SHORT).show();
									} else {
//										mHelper.consumeAsync(inventory.getPurchase(SKU), null);
										Toast.makeText(c, "You already paid to disable ads!", Toast.LENGTH_SHORT).show();
									}
								}
							});
							return;
						} else if (purchase.getSku().equals(SKU)) {
							LocalDatabaseHandler dbl = new LocalDatabaseHandler(c);
							dbl.setStuffValue("premium", "true");
						}
					}
				});
			}
		});
	}

}
