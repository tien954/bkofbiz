package src.org.ofbiz.bkeuniv.researchdomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;
import src.org.ofbiz.utils.BKEunivUtils;

public class ResearchDomain {
	public final static String module = ResearchDomain.class.getName();
	
	public static String createResearchDomainRequestResponse(HttpServletRequest request, HttpServletResponse response){
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Locale locale = UtilHttp.getLocale(request);
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		GenericValue staff = (GenericValue)request.getSession().getAttribute("staff");
		Map<String, Object> context = FastMap.newInstance();
		context.put("researchDomainId",request.getParameter("researchDomainId"));
		context.put("researchDomainName",request.getParameter("researchDomainName"));
		try{
			Map<String, Object> resultNewResearchDomain = dispatcher.runSync("createResearchDomain", context);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(resultNewResearchDomain), response, 200);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "success";
	}
	
	public static Map<String, Object> getResearchDomain(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String researchDomainId = (String)context.get("researchDomainId");		
		String researchDomainName = (String)context.get("researchDomainName");		
		
		try {
			 Map<String, Object> result = ServiceUtil.returnSuccess();
			 EntityCondition entity;
			 
			 EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			 List<GenericValue> list;
			 if(researchDomainId == null) {
					list = delegator.findList("ResearchDomain", null, null, null, findOptions, true);					
			 }
			  else {				
				  entity = EntityCondition.makeCondition("researchDomainId", EntityOperator.EQUALS, researchDomainId);				
				  list = delegator.findList("ResearchDomain", entity, null, null, findOptions, true);	
			}
			 result.put("researchDomain", list);
			 return result;						 
	
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
  			return rs; 			
  		}				
	}
	
	public static Map<String, Object> createResearchDomain(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String researchDomainId = (String) context.get("researchDomainId");
		String researchDomainName = (String) context.get("researchDomainName");
		
		GenericValue gv = delegator.makeValue("ResearchDomain");

		try {
			gv.put("researchDomainId", researchDomainId);
			gv.put("researchDomainName", researchDomainName);
			
			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		retSucc.put("researchDomain", gv);
		retSucc.put("message", "Create new row");
		return retSucc;
	}
	
	public static Map<String, Object> deleteResearchDomain(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String researchDomainId = (String)context.get("researchDomainId");
        try{
        	GenericValue gv = delegator.findOne("ResearchDomain", UtilMisc.toMap("researchDomainId",researchDomainId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + researchDomainId);
        	} else {
        		retSucc.put("result", "not found record with id: " + researchDomainId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateResearchDomain(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String researchDomainId = (String) context.get("researchDomainId");
		String researchDomainName = (String) context.get("researchDomainName");
		
		try{
			GenericValue gv = delegator.findOne("ResearchDomain", false, UtilMisc.toMap("researchDomainId",researchDomainId));
			if(gv != null){
				
				gv.put("researchDomainId", researchDomainId);
				gv.put("researchDomainName", researchDomainName);
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("researchDomainId", researchDomainId);
				rs.put("researchDomainName", researchDomainName);
				
				retSucc.put("researchDomain", rs);
        		retSucc.put("message", "updated record with id: " + researchDomainId);
        	} else {
        		retSucc.put("message", "not found record with id: " + researchDomainId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}