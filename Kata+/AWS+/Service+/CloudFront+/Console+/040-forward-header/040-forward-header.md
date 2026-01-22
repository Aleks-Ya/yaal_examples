# 040-forward-header


## Task
Forward header `CloudFront-Viewer-Country` to an Edge Function.

## Steps
1. Create a CloudFront Function
    1. Create Function
    	1. Name: `kata-f-forward-header`
    2. Publish Function
    	1. Function code:
			```javascript
			async function handler(event) {
			    var headers = event.request.headers;
			    var countryCode = 'Unknown';
			    if (headers['cloudfront-viewer-country']) {
			        countryCode = headers['cloudfront-viewer-country'].value;
			    }
			    var responseBody = `Country: ${countryCode}`;
			    var response = {
			        statusCode: 200,
			        statusDescription: 'OK',
			        headers: {
			            'content-type': { value: 'text/plain' }
			        },
			        body: responseBody
			    };
			    return response;
			}
			```
		2. Test the Function
		3. Publish The Function
2. Create a Distribution
	1. Create Distribution
	    1. Choose a plan: `Pay as you go`
	    2. Distribution name: `kata-distribution-forward-header`
	    3. Origin type: `Other`
	    4. Custom origin: `https://cat-bounce.com`
	    5. Web Application Firewall (WAF): `Web Application Firewall (WAF)`
	2. Configure redirect
		1. Edit the default Behavior
			1. Cache policy
				1. Create cache policy
					1. Name: `kata-policy-forward-header`
					2. Headers: `Include the following headers`
						1. Add header: `CloudFront-Viewer-Country`
					3. Query strings: `None`
					4. Cookies: `None`
			2. Function associations:
				1. Viewer request: 
					1. Function type: `CloudFront Functions`
					2. Function ARN / Name: `kata-f-forward-header`
	3. Wait when Distribution deployment finishes
3. Test
    1. Copy "Distribution domain name"
    2. Open the "Distribution domain name" in browser
    3. See https://theuselessweb.com opened

## Cleanup
1. Delete Distribution
    1. Disable the Distribution
    2. Delete the Distribution
2. Delete Function: `kata-f-forward-header`

## History
- 2026-01-25 success
