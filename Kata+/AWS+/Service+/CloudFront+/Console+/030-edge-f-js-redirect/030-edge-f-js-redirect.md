# 030-edge-f-js-redirect

## Task
Redirect client request with a CloudFront Edge Fuction (JavaScript).

## Steps
1. Create a CloudFront Function
    1. Create Function
    	1. Name: `kata-f-edge-f-js-redirect`
    2. Publish Function
    	1. Function code:
			```javascript
			async function handler(event) {
    			var response = {
        			statusCode: 302,
        			statusDescription: 'Found',
        			headers: {
            			"location": { "value": 'https://theuselessweb.com' }
        			}
    			};
    			return response;
			}
			```
		2. Test the Function
		3. Publish The Function
2. Create a Distribution
	1. Create Distribution
	    1. Choose a plan: `Free`
	    2. Distribution name: `kata-distribution-edge-f-js-redirect`
	    3. Origin type: `Other`
	    4. Custom origin: `https://cat-bounce.com`
	2. Configure redirect
		1. Edit the default Behavior
		2. Function associations:
			1. Viewer request: 
				1. Function type: `CloudFront Functions`
				2. Function ARN / Name: `kata-f-edge-f-js-redirect`
3. Test
    1. Copy "Distribution domain name"
    2. Open the "Distribution domain name" in browser
    3. See https://theuselessweb.com opened

## Cleanup
1. Delete Distribution
    1. Disable the Distribution
    2. Delete the Distribution
2. Delete Function: `kata-f-edge-f-js-redirect`

## History
- 2026-01-22 success
