<script>
    export let size = "50px"
    export let margin = ""
</script>

<div class="spinner" style="width: {size};height: {size}; margin: {margin}"></div>

<style lang="scss">
    .spinner {
        border-radius: 100%;
        border-left: solid 3px var(--mainColor);
        animation: spin 1s infinite linear;
    }

    @mixin keyframes($name) {
        @keyframes #{$name} {
            @content;
        }
    }

    @include keyframes(spin) {
        0% {
            transform: rotate(0deg);
        }
        
        100% {
            transform: rotate(360deg);
        }
    }
</style>
