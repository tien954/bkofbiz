package src.org.ofbiz.bkeuniv.foreignlanguagecatalog;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastList;
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

public class ForeignLanguageCatalog{
	public static Map<String, Object> getForeignLanguageCatalog(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String foreignLanguageCatalogCode = (String)context.get("foreignLanguageCatalogCode");		
		String foreignLanguageCatalogId = (String)context.get("foreignLanguageCatalogId");		
		String foreignLanguageCatalogName = (String)context.get("foreignLanguageCatalogName");
	
		
		try {
			 Map<String, Object> result = ServiceUtil.returnSuccess();
			 EntityCondition entity;
			 
			 EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			 List<GenericValue> list;
			 if(foreignLanguageCatalogCode == null) {
					list = delegator.findList("ForeignLanguageCatalog", null, null, null, findOptions, true);
					
			 }
			  else {				
				  entity = EntityCondition.makeCondition("foreignLanguageCatalogCode", EntityOperator.EQUALS, foreignLanguageCatalogCode);				
				  list = delegator.findList("ForeignLanguageCatalog", entity, null, null, findOptions, true);	
			}
			 result.put("foreignLanguageCatalog", list);
			 return result;						 
	
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
  			return rs;
  			
  		}
	}


	
	public static Map<String, Object>createAForeignLanguageCatalog(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String foreignLanguageCatalogId = (String) context.get("foreignLanguageCatalogId");
		String foreignLanguageCatalogName = (String) context.get("foreignLanguageCatalogName");
					
		GenericValue gv = delegator.makeValue("ForeignLanguageCatalog");

		gv.put("foreignLanguageCatalogCode", delegator.getNextSeqId("ForeignLanguageCatalog"));

		try {
			gv.put("foreignLanguageCatalogId", foreignLanguageCatalogId);
			gv.put("foreignLanguageCatalogName", foreignLanguageCatalogName);
			

			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		Map<String, Object> mapForeignLanguageCatalog = FastMap.newInstance();
		mapForeignLanguageCatalog.put("foreignLanguageCatalogCode", gv.getString("foreignLanguageCatalogCode"));
		mapForeignLanguageCatalog.put("foreignLanguageCatalogId", foreignLanguageCatalogId);
		mapForeignLanguageCatalog.put("foreignLanguageCatalogName", foreignLanguageCatalogName);		
		
		retSucc.put("foreignLanguageCatalog", mapForeignLanguageCatalog);

		return retSucc;
	}
	

	
	public static Map<String, Object> deleteForeignLanguageCatalog(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String foreignLanguageCatalogCode = (String)context.get("foreignLanguageCatalogCode");
        try{
        	GenericValue gv = delegator.findOne("ForeignLanguageCatalog", UtilMisc.toMap("foreignLanguageCatalogCode",foreignLanguageCatalogCode), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + foreignLanguageCatalogCode);
        	} else {
        		retSucc.put("result", "not found record with id: " + foreignLanguageCatalogCode);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}

	public static Map<String, Object> updateForeignLanguageCatalog(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String foreignLanguageCatalogId = (String) context.get("foreignLanguageCatalogId");
		String foreignLanguageCatalogName = (String) context.get("foreignLanguageCatalogName");
		String foreignLanguageCatalogCode = (String) context.get("foreignLanguageCatalogCode");
		
		try{
			GenericValue gv = delegator.findOne("ForeignLanguageCatalog", false, UtilMisc.toMap("foreignLanguageCatalogCode",foreignLanguageCatalogCode));
			if(gv != null){
				gv.put("foreignLanguageCatalogId", foreignLanguageCatalogId);
				gv.put("foreignLanguageCatalogName", foreignLanguageCatalogName);				
				
				delegator.store(gv);	
        		retSucc.put("result", "updated record with id: " + foreignLanguageCatalogCode);
        	} else {
        		retSucc.put("result", "not found record with id: " + foreignLanguageCatalogCode);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
		
}