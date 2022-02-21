package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.data.PasswordData;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.PasswordService;
import util.SecurityAPIResponse;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class PasswordController extends Controller {

    @javax.inject.Inject
    private HttpExecutionContext ec;

    private final PasswordService service;

    @Inject
    public PasswordController(PasswordService service) {
        this.service = service;
    }


    /**
     * Nonblocking password validity.
     * POST /api/security/isvalid
     * @return true password is valid, otherwise false.
     */
    public CompletionStage<Result> isValid() {
        try {

            //loading json from request
            JsonNode jsonNode = request().body().asJson();

            if (jsonNode == null) {
                CompletableFuture<SecurityAPIResponse> result = CompletableFuture.supplyAsync(() ->
                        new SecurityAPIResponse(SecurityAPIResponse.HTTPStatus.INVALID_JSON_POST), ec.current());
                return result.thenApply(response -> ok(Json.toJson(response)));
            }

            //deserializing the json post in data class
            PasswordData passwordData = Json.fromJson(jsonNode, PasswordData.class);

            if (passwordData == null) {
                CompletableFuture<SecurityAPIResponse> result = CompletableFuture.supplyAsync(() ->
                        new SecurityAPIResponse(SecurityAPIResponse.HTTPStatus.BAD_REQUEST), ec.current());
                return result.thenApply(response -> ok(Json.toJson(response)));
            }

            //calling service class asynchronously
            CompletableFuture<SecurityAPIResponse> futureResponse = CompletableFuture.supplyAsync(() ->
                    service.isValid(passwordData), ec.current());

            //when the result is ready
            return futureResponse.thenApply(response -> ok(Json.toJson(response)));

        } catch (Exception e) {
            e.printStackTrace();
            CompletableFuture<SecurityAPIResponse> result = CompletableFuture.supplyAsync(() -> new SecurityAPIResponse(SecurityAPIResponse.HTTPStatus.INTERNAL_SERVER_ERROR, e.getMessage()), ec.current());
            return result.thenApply(response -> internalServerError(Json.toJson(response)));
        }
    }
}
