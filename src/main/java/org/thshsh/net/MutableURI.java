package org.thshsh.net;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MutableURI implements Cloneable {

	protected static final Logger LOGGER = LoggerFactory.getLogger(MutableURI.class);
	
	public static final String SEPARATOR = "/";
	public static final String SEPARATOR_NESTED_PATH = "!";
	public static final String SEPARATOR_PROTOCOL = SEPARATOR+SEPARATOR;
	public static final String SEPARATOR_NO_HOST = SEPARATOR+SEPARATOR;
	public static final String SEPARATOR_SCHEME = ":";
	public static final String SEPARATOR_PORT = ":";
	public static final String SEPARATOR_FRAGMENT = "#";
	public static final String SEPARATOR_QUERY = "?";
	public static final String SEPARATOR_USER = "@";

	
	//Only used to catch encoding errors, does not stay in sync with changes
	@SuppressWarnings("unused") 
	private URI internal;

	private transient Boolean opaque;
	private transient String scheme; // null ==> relative URI
	private transient String fragment;
	// Hierarchical URI components: [//<authority>]<path>[?<query>]
	private transient String authority; // Registry or server
	// Server-based authority: [<userInfo>@]<host>[:<port>]
	private transient String userInfo;
	private transient String host; // null ==> registry-based
	private transient int port = -1; // -1 ==> undefined
	// Remaining components of hierarchical URIs
	private transient String path; // null ==> opaque
	private transient String query;
	//volatile because
	private volatile transient String schemeSpecificPart;
	private volatile transient MutableURI schemeSpecificUri;
	
	//Raw values
	private transient String authorityRaw;
	private transient String queryRaw;
	private transient String pathRaw;
	private transient String fragmentRaw;
	private transient String userInfoRaw;
	private transient String schemeSpecificPartRaw;

	public MutableURI(String s) throws URISyntaxException {
		recalculate(s);
	}

	public MutableURI(URI uri) throws URISyntaxException {
		recalculate(uri);
	}

	private void recalculate() throws URISyntaxException {
		recalculate((String)null);
	}
	
	private void recalculate(String s) throws URISyntaxException {
		URI internal;
		if (s != null) {
			internal = new URI(s);
		} 
		else if(opaque){
			StringBuilder sb = new StringBuilder(schemeSpecificPart);
			if(path != null) {
				sb.append(SEPARATOR_NESTED_PATH);
				sb.append(path);
			}
			internal = new URI(scheme, sb.toString(),fragment);
		}
		else {
			internal = new URI(scheme, userInfo, host, port, path, query, fragment);
		}

		recalculate(internal);
	}

	private void recalculate(URI internal) throws URISyntaxException {
		this.internal = internal;
		this.schemeSpecificPart = null;
		this.schemeSpecificPartRaw = null;
		this.schemeSpecificUri = null;
		opaque = internal.isOpaque();
		scheme = internal.getScheme();
		fragment = internal.getFragment();
		fragmentRaw = internal.getRawFragment();
		authority = internal.getAuthority();
		authorityRaw = internal.getRawAuthority();
		userInfo = internal.getUserInfo();
		userInfoRaw = internal.getRawUserInfo();
		host = internal.getHost();
		port = internal.getPort();
		path = internal.getRawPath();
		pathRaw = internal.getRawPath();
		if (path != null && path.startsWith(SEPARATOR_NO_HOST)) host = "";
		query = internal.getQuery();
		queryRaw = internal.getRawQuery();
		//try to turn SSP into URI
		if(opaque) {
			schemeSpecificPart = internal.getSchemeSpecificPart();
			schemeSpecificPartRaw = internal.getRawSchemeSpecificPart();
			if(schemeSpecificPart.contains(SEPARATOR_NESTED_PATH)){
				int index = schemeSpecificPart.lastIndexOf(SEPARATOR_NESTED_PATH);
				String nestedUri = schemeSpecificPart.substring(0, index);
				String unnestedPath = schemeSpecificPart.substring(index+1);
				schemeSpecificPart = nestedUri;
				path = unnestedPath;
			}
			try {
				schemeSpecificUri = new MutableURI(schemeSpecificPart);
				schemeSpecificPart = schemeSpecificUri.toString();
			}
			catch(URISyntaxException e){}
		}
		redefineString();
	}

	public URI getURI() throws URISyntaxException {
		return new URI(toString());
	}

	public void resolvePath(String res) throws URISyntaxException {
		URI pu = new URI(path);
		URI pr = pu.resolve(res);
		this.path = pr.toString();
		redefineString();
	}

	public String getScheme() {
		return scheme;
	}
	
	public Boolean getOpaque(){
		return opaque;
	}
	
	public void setScheme(String scheme) throws URISyntaxException {
		this.scheme = scheme;
		recalculate();
	}

	public String getRawSchemeSpecificPart() {
		return schemeSpecificPartRaw;
	}

	public String getSchemeSpecificPart() {
		return schemeSpecificPart;
	}
	
	public MutableURI getSchemeSpecificUri(){
		return schemeSpecificUri;
	}
	
	public void setSchemeSpecificUri(MutableURI u) throws URISyntaxException{
		this.schemeSpecificUri = u;
		this.schemeSpecificPart = (u!=null)?u.toString():null;
		recalculate();
	}
	
	public MutableURI getInnerMostUri() throws URISyntaxException{
		MutableURI current = this;
		MutableURI inner = this;
		while(current!=null && current.getOpaque()){
			current = current.getSchemeSpecificUri();
			if(current != null) inner = current;
		}
		return inner;
	}


	public String getRawAuthority() {
		return authorityRaw;
	}

	public String getAuthority() {
		return authority;
	}

	public String getRawUserInfo() {
		return userInfoRaw;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public String getHost() {
		return host;
	}
	
	public void setHost(String s) throws URISyntaxException{
		this.host = s;
		recalculate();
	}

	public int getPort() {
		return port;
	}
	
	public void setPort(int port) throws URISyntaxException {
		this.port = port;
		recalculate();
	}

	public String getRawPath() {
		return pathRaw;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String s) throws URISyntaxException {
		if(!s.startsWith(SEPARATOR))s=SEPARATOR+s;
		this.path = s;
		recalculate();
	}

	public String getRawQuery() {
		return queryRaw;
	}

	public String getQuery() {
		return query;
	}

	public String getRawFragment() {
		return fragmentRaw;
	}

	public String getFragment() {
		return fragment;
	}
	
	public void setFragment(String s) throws URISyntaxException {
		this.fragment = s;
		recalculate();
	}

	private String string;

	private void redefineString() {
		string = null;
		defineString();
	}

	private void defineString() {
		if (string != null)
			return;

		StringBuffer sb = new StringBuffer();
		if (scheme != null) {
			sb.append(scheme);
			sb.append(SEPARATOR_SCHEME);
		}
		if (opaque) {
			sb.append(schemeSpecificPart);
			if(path != null){
				sb.append(SEPARATOR_NESTED_PATH);
				sb.append(path);
			}
		} else {
			if (host != null) {
				sb.append(SEPARATOR_PROTOCOL);
				if (userInfo != null) {
					sb.append(userInfo);
					sb.append(SEPARATOR_USER);
				}
				boolean needBrackets = ((host.indexOf(':') >= 0) && !host.startsWith("[") && !host.endsWith("]"));
				if (needBrackets) sb.append('[');
				sb.append(host);
				if (needBrackets) sb.append(']');
				if (port != -1) {
					sb.append(SEPARATOR_PORT);
					sb.append(port);
				}
			} else if (authority != null) {
				sb.append(SEPARATOR_PROTOCOL);
				sb.append(authority);
			}
			if (path != null)
				sb.append(path);
			if (query != null) {
				sb.append(SEPARATOR_QUERY);
				sb.append(query);
			}
		}
		if (fragment != null) {
			sb.append(SEPARATOR_FRAGMENT);
			sb.append(fragment);
		}
		string = sb.toString();
	}
	
	
	public String toString() {
		defineString();
		return string;
	}

	@Override
	public MutableURI clone() {
		try {
			return (MutableURI) super.clone();
		} 
		catch (CloneNotSupportedException e) {
			throw new Error("Could not clone",e);
		}
	}
	
	
}
