package src.org.ofbiz.bkeuniv.foreignlanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class ForeignLanguage{
	public static Map<String, Object> getForeignLanguage(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String foreignLanguageId = (String)context.get("foreignLanguageId");
		String staffId = (String)context.get("staffId");
		String listen = (String)context.get("listen");
		String speaking = (String)context.get("speaking");
		String reading = (String)context.get("reading");
		String writing = (String)context.get("writing");
		
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			EntityCondition entity;
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			List<GenericValue> list;
			if(foreignLanguageId == null) {
				list = delegator.findList("ForeignLanguage", null, null, null, findOptions, true);
			} else {				
				entity = EntityCondition.makeCondition("foreignLanguageId", EntityOperator.EQUALS, foreignLanguageId);				
				list = delegator.findList("ForeignLanguage", entity, null, null, findOptions, true);	
			}
			
			result.put("foreignLanguage", list);
			return result;
		
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createAForeignLanguage(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String staffId = (String) context.get("staffId");
		String listen = (String) context.get("listen");
		String speaking = (String) context.get("speaking");
		String reading = (String) context.get("reading");
		String writing = (String) context.get("writing");
		
		GenericValue gv = delegator.makeValue("ForeignLanguage");

		gv.put("foreignLanguageId", delegator.getNextSeqId("ForeignLanguage"));

		try {
			gv.put("staffId", staffId);
			gv.put("listen", listen);
			gv.put("speaking", speaking);
			gv.put("reading", reading);
			gv.put("writing", writing);

			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		Map<String, Object> mapForeignLanguage = FastMap.newInstance();
		mapForeignLanguage.put("foreignLanguageId", gv.getString("foreignLanguageId"));
		mapForeignLanguage.put("staffId", staffId);
		mapForeignLanguage.put("listen", listen);
		mapForeignLanguage.put("speaking", speaking);
		mapForeignLanguage.put("reading", reading);
		mapForeignLanguage.put("writing", writing);
		
		
		retSucc.put("foreignLanguage", mapForeignLanguage);

		return retSucc;
	}
	

	
	public static Map<String, Object> deleteForeignLanguage(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
    Delegator delegator = ctx.getDelegator();
    LocalDispatcher dispatcher = ctx.getDispatcher();
    
    GenericValue userLogin = (GenericValue) context.get("userLogin");
    Locale locale = (Locale) context.get("locale");        
    
    Map<String,Object> retSucc = ServiceUtil.returnSuccess();
    
    String foreignLanguageId = (String)context.get("foreignLanguageId");
    try{
    	GenericValue gv = delegator.findOne("ForeignLanguage", UtilMisc.toMap("foreignLanguageId",foreignLanguageId), false);
    	if(gv != null){
    		delegator.removeValue(gv);
    		retSucc.put("result", "deleted record with id: " + foreignLanguageId);
    	} else {
    		retSucc.put("result", "not found record with id: " + foreignLanguageId);
    	}
    	
    }catch(Exception ex){
    	ex.printStackTrace();
    	return ServiceUtil.returnError(ex.getMessage());
    }
    return retSucc;
}

	public static Map<String, Object> updateForeignLanguage(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String staffId = (String) context.get("staffId");
		String listen = (String) context.get("listen");
		String speaking = (String) context.get("speaking");
		String reading = (String) context.get("reading");
		String writing = (String) context.get("writing");
		String foreignLanguageId = (String) context.get("foreignLanguageId");
		
		try{
			GenericValue gv = delegator.findOne("ForeignLanguage", false, UtilMisc.toMap("foreignLanguageId",foreignLanguageId));
			if(gv != null){
				gv.put("staffId", staffId);
				gv.put("listen", listen);
				gv.put("speaking", speaking);
				gv.put("reading", reading);
				gv.put("writing", writing);
				
				delegator.store(gv);	
        		retSucc.put("result", "updated record with id: " + foreignLanguageId);
        	} else {
        		retSucc.put("result", "not found record with id: " + foreignLanguageId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
	
}



