package pl.poznan.put.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.exceptions.StructureNotFoundException;
import pl.poznan.put.session.SessionManager;
import pl.poznan.put.structure.PdbStructureChains;
import pl.poznan.put.util.ConfigService;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ChainsListController {

    private final SessionManager sessionManager;
    private final PdbStructureChains pdbStructureChains;
    private final ConfigService configService;

    @RequestMapping(value = "/chains/{sessionId}")
    public final HttpEntity<List<String>> chains(@PathVariable("sessionId") final String sessionId) {
        this.validateSession(sessionId);
        return new ResponseEntity<>(pdbStructureChains.loadFromStructure(this.sessionManager
                .getSession(UUID.fromString(sessionId)).getStructure()), HttpStatus.OK);
    }

    private void validateSession(final String sessionId) {
        if (!this.sessionManager.hasSession(UUID.fromString(sessionId))) {
            log.debug("Session {} is invalid.", sessionId);
            throw new StructureNotFoundException(sessionId);
        }
    }
}
