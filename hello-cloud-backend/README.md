# cloud-container-internal

Test these endpoints with httpie:

```shell
    http GET :8080/actuator/health
    http GET :8080/internal-api
    http POST :8080/internal-api name=samit age=45
    http PUT :8080/internal-api/1 name=samit age=45
    http PATCH :8080/internal-api/1 name=samit age=45
    http GET :8080/internal-api
```
